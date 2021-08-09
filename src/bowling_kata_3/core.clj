(ns bowling-kata-3.core)

(def frame-count 10)

(defn last-frame-strike?
  [frame-index frame]
  (and
    (= (inc frame-index) frame-count)
    (= 10 (first frame))))

(defn sum-rolls
  [[frames accum]]
  (loop [index 0
         sum 0]
    (if (< index frame-count)
      (let [frame (nth frames index)]
        (if
          (last-frame-strike? index frame)
          (recur
            (inc index)
            (+ sum 10))
          (recur
            (inc index)
            (+ sum
               (first frame)
               (last frame)))))
      [frames (+ accum sum)])))

(defn strike?
  [roll frames]
  (and
    (= 10 roll)
    (= 0 (mod (count frames) 2))))

(defn create-frames-and-sum-strike-bonus
  [[rolls accum]]
  (loop [index 0
         rolls-with-strike []
         strike-bonus 0]
    (if (< index (count rolls))
      (let [roll (nth rolls index)]
        (if (strike? roll rolls-with-strike)
          (let [next-roll (nth rolls (inc index))
                second-next-roll (nth rolls (+ index 2)) ]
            (recur
              (if (< (count rolls-with-strike) 18)
                (inc index)
                (+ index 3))
              (concat
                rolls-with-strike
                (if (< (count rolls-with-strike) 18)
                  [10 0]
                  [roll next-roll second-next-roll]))
              (+ strike-bonus next-roll second-next-roll)))
          (recur
            (inc index)
            (concat rolls-with-strike [roll])
            strike-bonus)))
      [(partition-all 2 rolls-with-strike)  (+ strike-bonus accum)])))

(defn spare?
  [frame]
  (and
    (not (= 10 (first frame)))
    (= 10 (+ (first frame) (second frame)))))

(defn sum-spare-bonus
  [[frames accum]]
  (loop [index 0
         spare-bonus 0]
    (if (< index 10)
      (let [current-frame (nth frames index)]
        (if (spare? current-frame)
          (let [next-frame (nth frames (inc index))]
            (recur (inc index) (+ spare-bonus (first next-frame))))
          (recur (inc index) spare-bonus)))
      [frames (+ accum spare-bonus)])))

(defn score
  [rolls]
  (->> [rolls 0]
       create-frames-and-sum-strike-bonus
       sum-spare-bonus
       sum-rolls
       last))

