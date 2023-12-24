(ns app.ui
  (:require
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))

(defsc Strategy [this {:strategy/keys [id description] :as props}]
  {:query [:strategy/id :strategy/description]
   :ident :strategy/id
   :initial-state {:strategy/id :param/id
                   :strategy/description :param/description}}
  (dom/div {:style {:marginLeft "20px"}} description))
(def ui-strategy (comp/factory Strategy {:kefyn :strategy/id}))
(defsc Person [this {:person/keys [id name strategies] :as props}]
  {:query [:person/id :person/name {:person/strategies (comp/get-query Strategy)}]
   :ident :person/id
   :initial-state {:person/id :param/id
                   :person/name :param/name
                   :person/strategies [{:id 1
                                        :description "Bet minimum on Pass Line before come out roll"}]}}
  (dom/div {:style {:marginBottom "20px"}} (dom/h3 {} name)
           (map ui-strategy strategies)))
(def ui-person (comp/factory Person {:keyfn :person/id}))

(defsc Root [this {:root/keys [people]}]
  {:query [{:root/people (comp/get-query Person)}]
   :initial-state {:root/people [{:id 1 :name "Jen"}
                                 {:id 2 :name "Josephine"}]}}
  (dom/div
    (dom/h2 "People")
    (map ui-person people)))