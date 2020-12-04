#lang racket
(require "opcodes.rkt")
(provide make-stack-machine)
(provide run-stack-machine)
(provide get-stack)
(provide get-varnames)
(provide get-consts)
(provide get-names)
(provide get-code)
(provide get-IC)
(provide empty-stack)
(provide make-stack)
(provide push)
(provide pop)
(provide top)

(define-namespace-anchor a)
(define ns (namespace-anchor->namespace a))

;; TODO 1:
;; Alegeți metoda de reprezentarea a unei stive.
;; Implementați:
(define empty-stack '())
(define (make-stack) '())

(define (push element stack)
  (cons element stack))
(define (top stack) (car stack))
(define (pop stack) (cdr stack))

;; TODO 2:
;; Alegeți metoda de reprezentare a unei mașini stivă.
;; Definiți make-stack-machine, acesta trebuie sa primeasca cele 4 segmente de date
;; Veți avea nevoie de o stivă pentru execuție și un counter ca să stiți
;; la ce instrucțiune sunteți.
(define (make-stack-machine stack co-varnames co-consts co-names co-code IC) (hash
  0 stack
  1 co-varnames
  2 co-consts
  3 co-names
  4 co-code
  5 IC
  ))

;; Definiți funcțiile `get-varnames`, `get-consts`, `get-names`,
;; `get-code`, `get-stack`, `get-IC` care primesc o mașina stivă și întorc
;; componenta respectivă

;; ex:
;; > (get-varnames (make-stack-machine empty-stack 'dummy-co-varnames (hash) (hash) (list) 0))
;; 'dummy-co-varnames
(define (get-varnames stack-machine)
  (hash-ref stack-machine 1))

;; ex:
;; > (get-consts (make-stack-machine empty-stack (hash) 'dummy-co-consts (hash) (list) 0))
;; 'dummy-co-consts
(define (get-consts stack-machine)
  (hash-ref stack-machine 2))

;; ex:
;; > (get-names (make-stack-machine empty-stack (hash) (hash) 'dummy-co-names (list) 0))
;; 'dummy-co-names
(define (get-names stack-machine)
  (hash-ref stack-machine 3))

;; ex:
;; > (get-code (make-stack-machine empty-stack (hash) (hash) (hash) 'dummy-co-code 0))
;; dummy-co-code
(define (get-code stack-machine)
  (hash-ref stack-machine 4))

;; Întoarce stiva de execuție.
;; ex:
;; > (get-code (make-stack-machine 'dummy-exec-stack (hash) (hash) (hash) (list) 0))
;; dummy-exec-stack
(define (get-stack stack-machine)
  (hash-ref stack-machine 0))

;; Întoarce instruction counterul.
;; ex:
;; > (get-code (make-stack-machine empty-stack (hash) (hash) (hash) (list) 0))
;; 0
(define (get-IC stack-machine)
  (hash-ref stack-machine 5))



(define symbols (list 'STACK 'CO-VARNAMES 'CO-CONSTS 'CO-NAMES 'CO-CODE 'INSTRUCTION-COUNTER))

;; TODO 3:
;; Definiți funcția get-symbol-index care gasește index-ul simbolului in listă.
(define (get-symbol-index symbol)
  (cond
    ((equal? symbol 'STACK) 0)
    ((equal? symbol 'CO-VARNAMES) 1)
    ((equal? symbol 'CO-CONSTS) 2)
    ((equal? symbol 'CO-NAMES) 3)
    ((equal? symbol 'CO-CODE) 4)
    (else 5)))

;; Definiți funcția update-stack-machine care intoarce o noua mașina stivă
;; înlocuind componenta corespondentă simbolului cu item-ul dat în paremetri.
;; > (get-varnames (update-stack-machine "new-varnames" 'CO-VARNAMES stack-machine))
;; "new-varnames"
;; > (get-varnames (update-stack-machine "new-names" 'CO-NAMES stack-machine))
;; "new-names"
(define (update-stack-machine item symbol stack-machine)
  (hash-set stack-machine (get-symbol-index symbol) item))

;; Definiți funcția push-exec-stack care primește o masină stivă și o valoare
;; și intoarce o noua mașina unde valoarea este pusă pe stiva de execuție
(define (push-exec-stack value stack-machine)
  (update-stack-machine (push value (get-stack stack-machine)) 'STACK stack-machine))

;;  Definiți funcția pop-exec-stack care primește o masină stivă
;;  și intoarce o noua mașina aplicând pop pe stiva de execuție.
(define (pop-exec-stack stack-machine)
  (update-stack-machine (pop (get-stack stack-machine)) 'STACK stack-machine))

;; TODO 4:
;; Definiți funcția run-stack-machine care execută operații pană epuizează co-code.
(define (POP_TOP arg stack-machine) (pop-exec-stack stack-machine))

(define (LOAD_CONST const stack-machine)
  (push-exec-stack (hash-ref (get-consts stack-machine) const) stack-machine))

(define (LOAD_GLOBAL func stack-machine)
  (push-exec-stack (hash-ref (get-names stack-machine) func) stack-machine))

(define (LOAD_FAST var stack-machine)
  (push-exec-stack (hash-ref (get-varnames stack-machine) var) stack-machine))

(define (STORE_FAST var stack-machine)
  (POP_TOP 0 (update-stack-machine
   (hash-set (get-varnames stack-machine) var (top (get-stack stack-machine)))
   'CO-VARNAMES
   stack-machine)))

(define (BINARY_MODULO arg stack-machine)
  (let ((TOS (top (get-stack stack-machine)))
        (TOS1 (top (pop (get-stack stack-machine))))
        )
    (push-exec-stack (remainder TOS1 TOS) (POP_TOP 0 (POP_TOP 0 stack-machine)))))

(define (BINARY_ADD arg stack-machine)
  (let ((TOS (top (get-stack stack-machine)))
        (TOS1 (top (pop (get-stack stack-machine))))
        )
    (push-exec-stack (+ TOS TOS1) (POP_TOP 0 (POP_TOP 0 stack-machine)))))

(define (BINARY_SUBTRACT arg stack-machine)
  (let ((TOS (top (get-stack stack-machine)))
        (TOS1 (top (pop (get-stack stack-machine))))
        )
    (push-exec-stack (- TOS1 TOS) (POP_TOP 0 (POP_TOP 0 stack-machine)))))

(define INPLACE_ADD BINARY_ADD)

(define INPLACE_SUBTRACT BINARY_SUBTRACT)

(define INPLACE_MODULO BINARY_MODULO)

(define (JUMP_ABSOLUTE target stack-machine)
  (update-stack-machine target 'INSTRUCTION-COUNTER stack-machine))

(define (COMPARE_OP i stack-machine)
  (let ((TOS (top (get-stack stack-machine)))
        (TOS1 (top (pop (get-stack stack-machine))))
        (op (get-cmpop i)))
    (push-exec-stack (op TOS1 TOS) (POP_TOP 0 (POP_TOP 0 stack-machine)))))

(define (POP_JUMP_IF_FALSE target stack-machine)
  (if (not (top (get-stack stack-machine)))
      (JUMP_ABSOLUTE target (POP_TOP 0 stack-machine))
      (POP_TOP 0 stack-machine)
      ))

(define (POP_JUMP_IF_TRUE target stack-machine)
  (if (top (get-stack stack-machine))
      (JUMP_ABSOLUTE target (POP_TOP 0 stack-machine))
      (POP_TOP 0 stack-machine)
      ))

(define (SETUP_LOOP i stack-machine) stack-machine)

(define (GET_ITER i stack-machine) stack-machine)

(define (POP_BLOCK i stack-machine) stack-machine)

(define (FOR_ITER delta stack-machine)
  (if (null? (top (get-stack stack-machine)))
      (update-stack-machine (+ (get-IC stack-machine) (+ delta 2)) 'INSTRUCTION-COUNTER (POP_TOP 0 stack-machine))
      (let ((TOS (top (get-stack stack-machine))))
        (push-exec-stack (car TOS) (push-exec-stack (cdr TOS) (POP_TOP 0 stack-machine)))
        )))

(define (pop-argc argc stack-machine)
  (if (zero? argc)
      (POP_TOP 0 stack-machine)
      (pop-argc (sub1 argc) (POP_TOP 0 stack-machine))
      ))

(define (arg-list argc stack)
  (if (zero? argc)
      '()
      (cons (top stack) (arg-list (sub1 argc) (pop stack)))
      ))

(define (prod L)
  (apply * L))

(define (CALL_FUNCTION argc stack-machine)
  (if (> argc 1)
      (push-exec-stack (prod (arg-list argc (get-stack stack-machine))) (pop-argc argc stack-machine))
      (push-exec-stack
       ((eval (get-function (top (pop (get-stack stack-machine)))) ns) (top (get-stack stack-machine)))
       (pop-argc argc stack-machine))
      ))

(define (func stack-machine) (car (list-ref (get-code stack-machine) (quotient (get-IC stack-machine) 2))))

(define (arg stack-machine) (cdr (list-ref (get-code stack-machine) (quotient (get-IC stack-machine) 2))))

(define (inc_IC stack-machine)
  (update-stack-machine (+ (get-IC stack-machine) 2) 'INSTRUCTION-COUNTER stack-machine))

(define (run-stack-machine stack-machine)
  (if (equal? (func stack-machine) 'RETURN_VALUE)
      stack-machine
      (run-stack-machine
       ((eval (func stack-machine) ns) (arg stack-machine) (inc_IC stack-machine)))
    ))
