(ns fca-clj.functions
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [conexp.fca.contexts :refer :all]
            [conexp.fca.lattices :refer [concept-lattice]]
            [conexp.fca.implications :refer :all]
            [conexp.fca.exploration :refer :all]
            [conexp.fca.pqcores :refer :all]
            [conexp.math.sampling :refer [minimals-plus]]
            [conexp.gui.draw :refer :all]
            [conexp.gui.draw.scenes :refer [save-image show-labels]]
            [conexp.io.contexts :refer [read-context write-context]]
            [conexp.io.lattices :refer [read-lattice write-lattice]]
            [conexp.io.implications :refer [read-implication write-implication]]
            [conexp.layouts :refer [standard-layout]]
            [conexp.layouts.dim-draw :refer [dim-draw-layout]])
(:gen-class))

(def func-list
  "Contains all Available Functions with Parameter Ecplanation."
 
  (sorted-map "save-random-context" ["Set of Objects" "Fill Rate" "Output File Path"]  
              "save-diag-context" ["Size" "Output File Path"]
              "save-adiag-context" ["Size" "Output File Path"]
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
              "context-reduced?" ["Context File Path"]
              "reduce-context" ["Context File Path" "Output File Path"]
              "object-derivation" ["Context File Path" "Set of Objects"]
              "attribute-derivation" ["Context File Path" "Set of Attributes"]
              "context-object-closure" ["Context File Path" "Set of Objects"]
              "context-attribute-closure" ["Context File Path" "Set of Attributes"]
              "dual-context" ["Context File Path" "Output File Path"]
              "invert-context" ["Context File Path" "Output File Path"]
              "context-apposition" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-subposition" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-composition" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-union" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-sum" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-intersection" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-product" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-semiproduct" ["Context File Path" "Context File Path" "Output File Path"] 
              "context-xia-product" ["Context File Path" "Context File Path" "Output File Path"] 
              "concept-lattice" ["Context File Path" "Output File Path"]
              "draw-concept-lattice" ["Context File Path"]
              "minimals-plus" ["Lattice File Path" "Number of Samples"]
              "close-under-implications" ["Implications File Path" "Starting Set"]
              "canonical-base" ["Context File Path" "Output File Path"]
              "luxenburger-base" ["Context File Path" "Output File Path" "Minimum Support" "Minimum Confidence"]
              "explore-attributes" ["Context File Path"]
              "equivalent-implications?" ["Implications File Path" "Implications File Path"]
              "compute-core" ["Context File Path" "P" "Q"]
              "ctx-core-sizes" ["Context File Path"]
              "core-lattice-sizes" ["Context File Path"]
              "large-ctx-lattice-sizes-partial" ["Context File Path" "Maximum Lattice Size"]))


(defn get-function
  "Convert function string to function."
  [func-str]
  (case func-str

    "save-random-context" (fn [objs fill-rate save-path]
                            (write-context :burmeister (rand-context (load-string objs) (load-string fill-rate)) save-path))

    "save-diag-context" (fn [size save-path]
                          (write-context :burmeister (diag-context (load-string size)) save-path))

    "save-adiag-context" (fn [size save-path]
                           (write-context :burmeister (adiag-context (load-string size)) save-path))

    "objects" (fn [ctx-path] (println (objects (read-context ctx-path))))

    "attributes" (fn [ctx-path] (println (attributes (read-context ctx-path))))

    "incidence" (fn [ctx-path] (println (incidence (read-context ctx-path))))

    "extents" (fn [ctx-path] (println (extents (read-context ctx-path))))

    "intents" (fn [ctx-path] (println (intents (read-context ctx-path))))

    "concepts" (fn [ctx-path] (println (concepts (read-context ctx-path))))

    "object-clarified?" (fn [ctx-path] (println (object-clarified? (read-context ctx-path))))

    "attribute-clarified?" (fn [ctx-path] (println (attribute-clarified? (read-context ctx-path))))

    "context-clarified?" (fn [ctx-path] (println (context-clarified? (read-context ctx-path))))

    "clarify-objects" (fn [ctx-path save-path]
                        (write-context :burmeister (clarify-objects (read-context ctx-path)) save-path))

    "clarify-attributes" (fn [ctx-path save-path]
                        (write-context :burmeister (clarify-attributes (read-context ctx-path)) save-path))

    "clarify-context" (fn [ctx-path save-path]
                        (write-context :burmeister (clarify-context (read-context ctx-path)) save-path))

    "up-arrows" (fn [ctx-path] (println (up-arrows (read-context ctx-path))))

    "down-arrows" (fn [ctx-path] (println (down-arrows (read-context ctx-path))))

    "context-reduced?" (fn [ctx-path] (println (context-reduced? (read-context ctx-path))))

    "reduce-context" (fn [ctx-path save-path]
                            (write-context :burmeister (reduce-context (read-context ctx-path)) save-path))

    "object-derivation" (fn [ctx-path objs]
                          (println (object-derivation (read-context ctx-path) (load-string objs))))

    "attribute-derivation" (fn [ctx-path attrs]
                          (println (attribute-derivation (read-context ctx-path) (load-string attrs))))

    "context-object-closure" (fn [ctx-path objs]
                          (println (context-object-closure (read-context ctx-path) (load-string objs))))

    "context-attribute-closure" (fn [ctx-path attrs]
                          (println (context-attribute-closure (read-context ctx-path) (load-string attrs))))

    "dual-context" (fn [ctx-path save-path]
             (write-context :burmeister (dual-context (read-context ctx-path)) save-path))

    "invert-context" (fn [ctx-path save-path]
               (write-context :burmeister (invert-context (read-context ctx-path)) save-path))

    "context-apposition" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-apposition (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-subposition" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-subposition (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-composition" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-composition (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-union" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-union (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-sum" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-sum (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-intersection" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-intersection (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-product" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-product (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-semiproduct" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-semiproduct (read-context ctx-path) (read-context ctx-path2)) save-path))

    "context-xia-product" (fn [ctx-path ctx-path2 save-path]
                   (write-context :burmeister (context-xia-product (read-context ctx-path) (read-context ctx-path2)) save-path))


    "draw-concept-lattice" (fn [ctx-path] 
                             (draw-concept-lattice (read-context ctx-path)))

    "draw-lattice" (fn [lat-path] 
                     (draw-lattice (read-lattice lat-path)))

    "concept-lattice" (fn [ctx-path save-path]
                        (write-lattice (concept-lattice (read-context ctx-path)) save-path))

    "minimals-plus" (fn [lat-path samples]
                      (println (minimals-plus (read-lattice lat-path) (load-string samples))))

    "close-under-implications" (fn [impl-path start]
                                 (println (close-under-implications (read-implication impl-path) (load-string start))))

    "canonical-base" (fn [ctx-path save-path]
                       (write-implication (canonical-base (read-context ctx-path)) save-path))

    "luxenburger-base" (fn [ctx-path save-path supp conf]
                         (write-implication (luxenburger-base (read-context ctx-path) (load-string supp) (load-string conf)) save-path))

    "explore-attributes" (fn [ctx-path]
                           (println (explore-attributes :context (read-context ctx-path))))

    "equivalent-implications?" ( fn [impl-path impl-path2]
                                (println (equivalent-implications? (read-implication impl-path) (read-implication impl-path2))))  

    "compute-core" (fn [ctx-path p q]
                     (println (compute-core (read-context ctx-path) (load-string p) (load-string q))))

    "ctx-core-sizes" (fn [ctx-path]
                       (println (ctx-core-sizes (read-context ctx-path))))

    "core-lattice-sizes" (fn [ctx-path]
                           (println (core-lattice-sizes (read-context ctx-path))))

    "large-ctx-lattice-sizes-partial" (fn [ctx-path size]
                                        (println (large-ctx-lattice-sizes-partial (read-context ctx-path) (load-string size))))
    nil))

