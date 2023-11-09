
;; Copyright ⓒ the conexp-clj developers; all rights reserved.
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file LICENSE at the root of this distribution.
;; By using this software in any fashion, you are agreeing to be bound by
;; the terms of this license.
;; You must not remove this notice, or any other, from this software.


;;Function todo:
;;(objects ctx)
;;(attributes ctx)
;;(incidence ctx)
;;(extents ctx)
;;(intents ctx)
;;(concepts)
;;weitere Funktionen aus contexts
;;canonical-base
;;ganter-base
;;save csv as context
;;(explore-attributes :context ctx)
;;number-of-linear-extensions

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

(def func-list ["draw-concept-lattice" 
                "save-concept-lattice" 
                "save-concept-lattice-dimdraw"])

(defn- get-function
  "Convert function string to function."
  [func-str]
  (case func-str
    "draw-concept-lattice" (fn [ctx-path] 
                                (draw-concept-lattice (read-context ctx-path)))

    "save-concept-lattice" (fn [ctx-path save-path] 
                                (let [ctx (read-context ctx-path)
                                      concept-lattice (concept-lattice ctx)
                                      layout (standard-layout concept-lattice)
                                      frame-and-scene (draw-layout layout 
                                                                   :visible true
                                                                   :dimension [1000 1000])
                                      scene (:scene frame-and-scene)]
                               (show-labels scene true)
                               (Thread/sleep 1000) ;; make sure that the scene is fully drawn
                               (save-image scene (io/as-file save-path) "png"))
                             (System/exit 0))

    "save-concept-lattice-dimdraw" (fn [ctx-path save-path] 
                                        (let [ctx (read-context ctx-path)
                                              concept-lattice (concept-lattice ctx)
                                              layout (dim-draw-layout concept-lattice)
                                              frame-and-scene (draw-layout layout 
                                                                           :visible true
                                                                           :dimension [1000 1000])
                                              scene (:scene frame-and-scene)]
                                       (show-labels scene true)
                                       (Thread/sleep 1000) ;; make sure that the scene is fully drawn
                                       (save-image scene (io/as-file save-path) "png"))
                                     (System/exit 0))                             
    nil))

(defn- get-args
  "Returns Parameter Explanation for Functions."
  [func]
  (case func
  
  "draw-concept-lattice" ["Context File Path"]
  "save-concept-lattice" ["Context File Path" "Output File Path"]
  "save-concept-lattice-dimdraw" ["Context File Path" "Output File Path"]
  nil))

(defn- display-help
  []
  (println "Enter -h for help.")
  (println "Use -f to enter a function followed by its parameters.")
  (println "Available functions with their parameters:")
  (doseq [f func-list]
     (println (str f " : " (get-args f))) 
     )
)



(defn- help 
  "Displays Instruction Text. *func* may be nil"
  [func]
  (if func (let [args (get-args func)]
              (if (.contains func-list func) 
                 (do
                    (println (str "The Function " func " requires the following arguments in order:"))
                    (doseq [arg args] (println arg)))

                  (do 
                     (println "The Function " func " is not recognized. The following Functions are supported:")
                     (doseq [f func-list] (println f)))))

            (display-help)))

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
         (help (:function options))
      :else
      (do
        
        (let [func-str (:function options)
              func (get-function func-str)] 

          (assert func-str "Function (-f, --function) must be specified.")       
          (assert func (str "The function " func-str " is not supported."))
          (apply func arguments)
          )))))
