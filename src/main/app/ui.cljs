(ns app.ui
  (:require
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))
(defsc Person [this {:person/keys [id name] :as props}]
  {:query [:person/id :person/name]
   :ident :person/id
   :initial-state {:person/id :param/id
                   :person/name :param/name}}
  (dom/div {:style {:marginBottom "20px"}} (dom/h3 {} name)))
(def ui-person (comp/factory Person {:keyfn :person/id}))

(defsc Root [this {:root/keys [people]}]
  {:query [{:root/people (comp/get-query Person)}]
   :initial-state {:root/people [{:id 1 :name "Jen"}
                                 {:id 2 :name "Josephine"}]}}
  (dom/div
    (dom/h2 "People")
    (map ui-person people)))