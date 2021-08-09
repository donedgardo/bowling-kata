(ns bowling-kata-3.core-spec
  (:require [speclj.core :refer :all]
            [bowling-kata-3.core :refer :all]))

(defn roll-many [n pins]
  (vec (repeat n pins)))

(describe "A gutter game"
  (it "should score 0"
    (should= 0 (score (roll-many 20 0)))))

(describe "A all 1 score game"
  (it "should score 20"
    (should= 20 (score (roll-many 20 1)))))

(describe "1 spare and 3 roll"
   (it "should score 13"
     (should= 16
       (score
         (concat
           [5 5]
           [3]
           (roll-many 17 0))))))

(describe "1 strike and 3 4 roll"
          (it "should score 24"
              (should= 24
                       (score
                         (concat
                           [10]
                           [3 4]
                           (roll-many 16 0))))))

(describe "perfect game"
          (it "should score 300"
              (should= 300
                       (score
                         (roll-many 12 10)))))




