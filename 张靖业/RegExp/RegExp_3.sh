#!/bin/sh

echo 输入您想要获取信息的贴吧名：
read name

wget -O tmp.html "https://tieba.baidu.com/f?ie=utf-8&kw=$name&fr=search"

echo
echo $name吧
# 显示签名
cat tmp.html | grep card_slogan | sed 's/.*<p.*">//g' | sed 's/<\/p>//g'
echo
echo ================================
echo
# 将帖子主题保存在临时文件中
cat tmp.html | grep -A 3 j_th_tit | grep "<a h" | sed 's/.*title="//g' | sed 's/" target.*//g' > tmptitle.txt
# 将帖子作者保存在临时文件中
cat tmp.html | grep -A 3 j_th_tit | grep 主题作者 | sed 's/.*title="//g' | sed 's/"//g' > tmpauthor.txt

maxline=$(wc -l tmptitle.txt | sed 's/\ .*//g')
current=0
while true; do
  if [ $current -gt $((maxline-1)) ]; then
    break
  fi
  cat tmptitle.txt | head -n $((current+1)) | tail -n 1
  cat tmpauthor.txt | head -n $((current+1)) | tail -n 1
  echo
  current=$((current+1))
done

rm tmptitle.txt tmpauthor.txt
