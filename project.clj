(defproject toolbox "0.1.0-SNAPSHOT"
  :description "Tools for doing everyday things in ClojureScript apps."
  :url "Tools for doing everyday things in ClojureScript apps."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  ;:plugins [[lein-cljsbuild "0.2.5"]]
  
  ;:dependencies [[org.clojure/clojure "1.4.0"]]
  
  #_:cljsbuild #_{:builds
              [{:source-path "src",
                :compiler {:output-to "toolbox.js",
                           :optimizations :advanced,
                           :pretty-print false,
                           ;:externs ["../externs.js"]
                           }}]}
  )
