(ns fca-clj.functions
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [conexp.fca.contexts :refer :all]
            [conexp.fca.lattices :refer [concept-lattice]]
            [conexp.gui.draw :refer :all]
            [conexp.gui.draw.scenes :refer [save-image show-labels]]
            [conexp.io.contexts :refer [read-context write-context]]
            [conexp.layouts :refer [standard-layout]]
            [conexp.layouts.dim-draw :refer [dim-draw-layout]])
(:gen-class))
 
;;Function todo:
;;rand-ctx
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

(def func-list ["save-random-context"
                "objects"
                "attributes"
                "incidence"
                "extents"
                "intents"
                "concepts"
                "object-clarified?"
                "attribute-clarified?"
                "context-clarified?"
                "clarify-objects"
                "clarify-attributes"
                "clarify-context"
                "up-arrows"
                "down-arrows"
                "reduced?"
                "reduce"
                "object-derivation"
                "attribute-derivation"
                "object-clojure"
                "attribute-clojure"
                "dual"
                "invert"
                "apposition"
                "subposition"
                "composition"
                "union"
                "sum"
                "intersection"
                "product"
                "semiproduct"
                "xia-product"

                
                "draw-concept-lattice" 
                "save-concept-lattice" 
                "save-concept-lattice-dimdraw"
                ])

(defn get-args
  "Returns Parameter Explanation for Functions."
  [func]
  (case func  
  "save-random-context" ["Set of Objects" "Fill Rate" "Output File Path"]  
  "objects" ["Context File Path"]
  "attributes" ["Context File Path"]
  "incidence" ["Context File Path"]
  "extents" ["Context File Path"]
  "intents" ["Context File Path"]
  "concepts" ["Context File Path"]
  "object-clarified?" ["Context File Path"]
  "attribute-clarified?" ["Context File Path"]
  "context-clarified" ["Context File Path"]
  "clarify-objects" ["Context File Path" "Output File Path"]
  "clarify-attributes" ["Context File Path" "Output File Path"]
  "clarify-context" ["Context File Path" "Output File Path"]
  "up-arrows" ["Context File Path"]
  "down-arrows" ["Context File Path"]
  "reduced?" ["Context File Path"]
  "reduce" ["Context File Path" "Output File Path"]
  "object-derivation" ["Context File Path" "Set of Objects"]
  "attribute-derivation" ["Context File Path" "Set of Attributes"]
  "object-clojure" ["Context File Path" "Set of Objects"]
  "attribute-clojure" ["Context File Path" "Set of Attributes"]
  "dual" ["Context File Path" "Output File Path"]
  "invert" ["Context File Path" "Output File Path"]
  "apposition" ["Context File Path" "Context File Path" "Output File Path"] 
  "subposition" ["Context File Path" "Context File Path" "Output File Path"] 
  "composition" ["Context File Path" "Context File Path" "Output File Path"] 
  "union" ["Context File Path" "Context File Path" "Output File Path"] 
  "sum" ["Context File Path" "Context File Path" "Output File Path"] 
  "intersection" ["Context File Path" "Context File Path" "Output File Path"] 
  "product" ["Context File Path" "Context File Path" "Output File Path"] 
  "semiproduct" ["Context File Path" "Context File Path" "Output File Path"] 
  "xia-product" ["Context File Path" "Context File Path" "Output File Path"] 


  "draw-concept-lattice" ["Context File Path"]
  "save-concept-lattice" ["Context File Path" "Output File Path"]
  "save-concept-lattice-dimdraw" ["Context File Path" "Output File Path"]
  nil))


(defn get-function
  "Convert function string to function."
  [func-str]
  (case func-str

    "save-random-context" (fn [objs fill-rate save-path]
                            (write-context :burmeister (rand-context (load-string objs) (load-string fill-rate)) save-path))

    "objects" (fn [ctx-path] (println (objects (read-context ctx-path))))

    "attributes" (fn [ctx-path] (println (attributes (read-context ctx-path))))

    "incidence" (fn [ctx-path] (println (incidence (read-context ctx-path))))

    "extents" (fn [ctx-path] (println (extents (read-context ctx-path))))

    "intents" (fn [ctx-path] (println (intents (read-context ctx-path))))

    "concepts" (fn [ctx-path] (println (concepts (read-context ctx-path))))

    "object-clarified?" (fn [ctx-path] (println (object-clarified? (read-context ctx-path))))

    "attribute-clarified?" (fn [ctx-path] (println (attribute-clarified? (read-context ctx-path))))

    "context-clarified?" (fn [ctx-path] (println (object-clarified? (read-context ctx-path))))

    "clarify-objects" (fn [ctx-path save-path]
                        (write-context :burmeister (clarify-objects (read-context ctx-path)) save-path))

    "clarify-attributes" (fn [ctx-path save-path]
                        (write-context :burmeister (clarify-attributes (read-context ctx-path)) save-path))

    "clarify-context" (fn [ctx-path save-path]
                        (write-context :burmeister (clarify-context (read-context ctx-path)) save-path))

    "up-arrows" (fn [ctx-path] (println (up-arrows (read-context ctx-path))))

    "down-arrows" (fn [ctx-path] (println (down-arrows (read-context ctx-path))))

    "reduced?" (fn [ctx-path] (println (context-reduced? (read-context ctx-path))))

    "reduce" (fn [ctx-path save-path]
                            (write-context :burmeister (reduce-context (read-context ctx-path)) save-path))

    "object-derivation" (fn [ctx-path objs]
                          (println (object-derivation (read-context ctx-path) (load-string objs))))

    "attribute-derivation" (fn [ctx-path attrs]
                          (println (attribute-derivation (read-context ctx-path) (load-string attrs))))

    "object-clojure" (fn [ctx-path objs]
                          (println (context-object-closure (read-context ctx-path) (load-string objs))))

    "attribute-clojure" (fn [ctx-path attrs]
                          (println (context-attribute-closure (read-context ctx-path) (load-string attrs))))

    "dual" (fn [ctx-path save-path]
             (write-context :burmeister (dual-context (read-context ctx-path)) save-path))

    "invert" (fn [ctx-path save-path]
               (write-context :burmeister (invert-context (read-context ctx-path)) save-path))

    "apposition" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-apposition (read-context ctx-path) (read-context ctx-path2)) save-path))

    "subposition" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-subposition (read-context ctx-path) (read-context ctx-path2)) save-path))

    "composition" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-composition (read-context ctx-path) (read-context ctx-path2)) save-path))

    "union" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-union (read-context ctx-path) (read-context ctx-path2)) save-path))

    "sum" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-sum (read-context ctx-path) (read-context ctx-path2)) save-path))

    "intersection" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-intersection (read-context ctx-path) (read-context ctx-path2)) save-path))

    "product" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-product (read-context ctx-path) (read-context ctx-path2)) save-path))

    "semiproduct" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-semiproduct (read-context ctx-path) (read-context ctx-path2)) save-path))

    "xia-product" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-xia-product (read-context ctx-path) (read-context ctx-path2)) save-path))


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

;testing-data/living-beings-and-water.cxt
;test.png
