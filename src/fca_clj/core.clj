;; Copyright ⓒ the conexp-clj developers; all rights reserved.
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file LICENSE at the root of this distribution.
;; By using this software in any fashion, you are agreeing to be bound by
;; the terms of this license.
;; You must not remove this notice, or any other, from this software.

(ns fca-clj.core
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [conexp.fca.lattices :refer [concept-lattice]]
            [conexp.gui.draw :refer :all]
            [conexp.gui.draw.scenes :refer [save-image show-labels]]
            [conexp.io.contexts :refer [read-context]]
            [conexp.layouts :refer [standard-layout]]
            [conexp.layouts.dim-draw :refer [dim-draw-layout]])
  (:gen-class))

(def cli-options
  "Command line options for fca-clj."
  [["-f" "--function FUNCTION" "Function to be executed."]
   ["-i" "--input FILE" "Input file path (for context file)."]
   ["-o" "--output FILE" "Output file path (for output of the fca analysis)."]
   ["-h" "--help"]])

(defn- get-function
  "Convert function string to function."
  [options]
  (case (:function options)
    "draw-concept-lattice" (fn [context] 
                             (draw-concept-lattice context))
    "save-concept-lattice" (fn [context] 
                             (let [concept-lattice (concept-lattice context)
                                   layout (standard-layout concept-lattice)
                                   frame-and-scene (draw-layout layout 
                                                                :visible true
                                                                :dimension [1000 1000])
                                   scene (:scene frame-and-scene)]
                               (show-labels scene true)
                               (Thread/sleep 500) ;; make sure that the scene is fully drawn
                               (save-image scene (io/as-file (:output options)) "png"))
                             (System/exit 0))
    "save-concept-lattice-dimdraw" (fn [context] 
                                     (let [concept-lattice (concept-lattice context)
                                           layout (dim-draw-layout concept-lattice)
                                           frame-and-scene (draw-layout layout 
                                                                        :visible true
                                                                        :dimension [1000 1000])
                                           scene (:scene frame-and-scene)]
                                       (show-labels scene true)
                                       (Thread/sleep 500) ;; make sure that the scene is fully drawn
                                       (save-image scene (io/as-file (:output options)) "png"))
                                     (System/exit 0))
    nil))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    
    ;; Error handling
    (when errors
      (doseq [error errors]
        (println error))
      (System/exit 1))
    
    (cond
      ;; Print help if requested
      (contains? options :help)
      (println summary)
      
      :else
      (do
        (assert (:function options) "Function (-f, --function) must be specified.")
        (assert (:input options) "Input file (-i, --input) must be specified.")
        (assert (:output options) "Output file (-o, --output) must be specified.")
        
        (let [context (read-context (:input options))
              function (get-function options)]
          (assert function (str "The function " (:function options) " is not supported."))
          (function context))))))
