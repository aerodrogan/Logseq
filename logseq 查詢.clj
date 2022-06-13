#+BEGIN_QUERY
{:title [ "📅 七天內有規劃(排程或期限)的事項"] 
  :query [:find (pull ?block [*])
    :in $ ?start ?next
    :where
      (or
        [?block :block/scheduled ?d]
        [?block :block/deadline ?d])
      [(>= ?d ?start)]
      [(<= ?d ?next)]]
  :result-transform (fn [result]
        (sort-by (fn [h]
                 (min [(get-in h [:block/deadline])] [(get-in h [:block/scheduled])])) result))
  :inputs [:today :7d-after]
  :collapsed? true
  :breadcrumb-show? false} ;;檢索結果是否顯示麵包屑路徑 
#+END_QUERY

#+BEGIN_QUERY
{:title "📆 按日期排序 Todo 待辦 / 執行 Doing 事項"
  :query [:find (pull ?b [*])
    :in $ ?current-page
    :where
      (task ?b #{"NOW" "LATER" "TODO" "DOING"})]
  :inputs [:current-page]
  :result-transform (fn [result]
        (sort-by (fn [h]
                 (min [(get-in h [:block/deadline])] [(get-in h [:block/scheduled])])) result))
  :collapsed? true
  :breadcrumb-show? false} ;;檢索結果是否顯示麵包屑路徑
#+END_QUERY