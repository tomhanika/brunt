
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
            [conexp.layouts.dim-draw :refer [dim-draw-layout]]
            [fca-clj.functions :refer :all])
  (:gen-class))

(def cli-options
  "Command line options for fca-clj."
  [["-f" "--function FUNCTION" "Function to be executed."]
   ["-h" "--help"]])



(defn- display-help
  []
  (println "Enter -h for help.")
  (println "Use -f to enter a function followed by its parameters.\n")
  (println "Sets can be entered as follows: \"#{1 2 3}\"")
  (println "Sets containing string values need to be entered as follows: \"#{'a' 'b' 'c'}\"")
  (println "Available functions with their parameters:\n")
  (doseq [f (keys func-list)]
     (println (str f " : " (func-list f))) 
     )
)



(defn- help 
  "Displays Instruction Text. *func* may be nil"
  [func]
  (if func (let [args (func-list func)]
              (if args
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
