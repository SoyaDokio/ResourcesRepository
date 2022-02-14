#!/bin/bash
directory_path="/mnt/docker/transmission/download/complete"

cd /mnt/docker/transmission_config
if [ ! -d "/mnt/docker/transmission_config/remote_copy_log" ];then
  mkdir remote_copy_log
fi
cd remote_copy_log
if [ ! -d "$(date +"%Y%m")" ];then
  mkdir "$(date +"%Y%m")"
fi
cd "$(date +"%Y%m")"
if [ ! -f "" ];then
touch $(date +"%Y%m%d").log
fi
today_log_path=/mnt/docker/transmission_config/remote_copy_log/$(date +"%Y%m")/$(date +"%Y%m%d").log

filename_list=$(ls -l /mnt/docker/transmission/download/complete | awk '{print "/mnt/docker/transmission/download/complete/" $9}' )
loop_not_first_flg=false
for filename in ${filename_list[*]}
do
  if [[loop_not_first_flg -eq true]];then
    continue
  fi
  scp /mnt/samba/downloads/complete/filename root@192.168.17.112:/Elements/ > $today_log_path
  loop_not_first_flg=true
done

# scp命令时候需要输入密码，运用以下方法可以免除密码输入
# https://blog.csdn.net/liuhuashui123/article/details/52190881

# 定时任务配置：
#   用系统自带crontab配置，每天早上6点10分执行脚本。crontab -e 里边添加如下内容：
#   10 06 * * * /data/mv_film_cron.sh
