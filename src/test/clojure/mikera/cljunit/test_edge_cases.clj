(ns mikera.cljunit.test-edge-cases
  (:import [clojure.lang RT Compiler Compiler$C])
  (:use clojure.test))

;; expression info test causes strange edge case because Compiler.LOADER is unbound

(defn expression-info-internal
  [expr]
  (let [fn-ast (Compiler/analyze Compiler$C/EXPRESSION expr)
        expr-ast (.body (first (.methods fn-ast)))]
    (when (.hasJavaClass expr-ast)
      {:class (.getJavaClass expr-ast)
       :primitive? (.isPrimitive (.getJavaClass expr-ast))})))

(defn expression-info
  "Uses the Clojure compiler to analyze the given s-expr.  Returns
  a map with keys :class and :primitive? indicating what the compiler
  concluded about the return value of the expression.  Returns nil if
  no type info can be determined at compile-time.
  
  Example: (expression-info '(+ (int 5) (float 10)))
  Returns: {:class float, :primitive? true}"
  [expr]
  (expression-info-internal `(fn [] ~expr)))

(deftest test-expression-info
  (testing "expression "
    (is (:primitive (expression-info '(+ 2 3))))))

