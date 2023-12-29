(ns app.ui
  (:require
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))
(defsc Strategy [this {:strategy/keys [id description] :as props}]
  {:query [:strategy/id :strategy/description]
   :ident :strategy/id
   :initial-state {:strategy/id :param/id
                   :strategy/description :param/description}}
  (dom/div {:style {:marginLeft "20px"}} (dom/p {} description)))
(def ui-strategy (comp/factory Strategy {:keyfn :strategy/id}))
(defsc Bet [this {:bet/keys [id name] :as props}]
  {:query [:bet/id :bet/name]
   :ident :bet/id
   :initial-state {:bet/id :param/id
                   :bet/name :param/name}}
  (dom/div {:style {:marginBottom "20px"}}
           (dom/h3 {} name)))
(def ui-bet (comp/factory Bet {:keyfn :bet/id}))
; placed-bet has bet wager
; :query [:placed-bet/id {:placed-bet/bet (comp/get-query Bet)} :placed-bet/wager]
(defsc PlacedBet [this {:placed-bet/keys [id] :as props}]
  {:query [:placed-bet/id]
   :ident :placed-bet/id
   :initial-state {:placed-bet/id :param/id}}
  (dom/div {} "Placed Bet Id " id ))
(def ui-placed-bet (comp/factory PlacedBet {:keyfn :placed-bet/id}))
(defsc Person [this {:person/keys [id name strategies placed-bets] :as props}]
  {:query [:person/id :person/name {:person/strategies (comp/get-query Strategy)}]
   :ident :person/id
   :initial-state {:person/id :param/id
                   :person/name :param/name
                   :person/strategies [{:id 1 :description "On the pass line,
                                                            make the minimum bet,
                                                            before the come out roll"}]
                   :person/placed-bets [{:id 1}]}}
  (dom/div {:style {:marginBottom "20px"}}
           (dom/h3 {} name)
           (map ui-strategy strategies)))
(def ui-person (comp/factory Person {:keyfn :person/id}))
(defsc Table [this {:table/keys [id people bets] :as props}]
  {:query [:table/id {:table/people (comp/get-query Person)} {:table/bets (comp/get-query Bet)}]
   :ident :table/id
   :initial-state {:table/id :param/id
                   :table/people [{:id 1 :name "Jen"}
                                  {:id 2 :name "Jo"}]
                   :table/bets [{:id 1 :name "Pass Line"}]}}
  (dom/div {:style {:marginBottom "20px"}}
           (dom/h3 {} "Table [" id "]")
           (dom/div {} "(People)" (map ui-person people))
           (dom/div {} "(Bets)" (map ui-bet bets))))
(def ui-table (comp/factory Table {:keyfn :table/id}))

(defsc Root [this {:root/keys [tables]}]
  {:query [{:root/tables (comp/get-query Table)}]
   :initial-state {:root/tables [{:id 1}]}}
  (dom/div
    (map ui-table tables)))