;; Copyright ⓒ the conexp-clj developers; all rights reserved.
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file LICENSE at the root of this distribution.
;; By using this software in any fashion, you are agreeing to be bound by
;; the terms of this license.
;; You must not remove this notice, or any other, from this software.

(defproject fca-clj "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "A tool for formal concept analysis based on conexp-clj."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure   "1.11.1"]
                 [org.clojure/tools.cli "1.0.219"]
                 [conexp-clj            "2.3.0"]]
  :main ^:skip-aot fca-clj.core
  :target-path "builds/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
