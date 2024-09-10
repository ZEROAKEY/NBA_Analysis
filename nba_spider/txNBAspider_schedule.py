#-*- codeing =utf-8 -*-
#-*- codeing =utf-8 -*-
# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

#-*- codeing =utf-8 -*-
import json
import requests
import time
import sqlite3

headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36'}

def store_json(obj):
    filename='nba_schedule.json'
    with open(filename, 'w', encoding='utf-8') as file_obj:
        """参数1:需要转化的数据
           参数2:文件对象
           参数3:是否按字母排序
           参数4:json内容左边的空格数
           参数5:想输出真正的中文需要指定ensure_ascii=False，如果为True则会输出这种类型："name": "\u7530\u665a\u6770",
        """
        json.dump(obj, file_obj, sort_keys=False, indent=4, ensure_ascii=False)

def load_json(path):
    with open(path, 'r', encoding='utf-8') as f:
        # print(json.load(f))
        return json.load(f)

def deal_store_data():
    # 读取 JSON 文件内容
    with open('nba_schedule.json', 'r', encoding='utf-8') as file:
        data = json.load(file)

    # 连接到 SQLite 数据库
    conn = sqlite3.connect('nba_total.db')
    cursor = conn.cursor()

    # 创建表（如果尚未创建）
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS nba_schedule (
        date TEXT,
        matchType TEXT,
        leftName TEXT,
        leftBadge TEXT,
        leftGoal INTEGER,
        rightName TEXT,
        rightBadge TEXT,
        rightGoal INTEGER
    )
    ''')

    # 遍历 JSON 数据并存储到数据库
    for date, matches in data['data'].items():
        for match in matches:
            matchType = match.get('matchType')
            leftName = match.get('leftName')
            leftBadge = match.get('leftBadge')
            leftGoal = match.get('leftGoal')
            rightName = match.get('rightName')
            rightBadge = match.get('rightBadge')
            rightGoal = match.get('rightGoal')
            # 插入数据到数据库
            cursor.execute('''
            INSERT INTO nba_schedule (date, matchType, leftName, leftBadge, leftGoal, rightName, rightBadge, rightGoal)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ''', (date, matchType, leftName, leftBadge, leftGoal, rightName, rightBadge, rightGoal))

    # 提交并关闭数据库连接
    conn.commit()
    conn.close()

    print("所有比赛数据已成功存储到数据库中。")

def main():
    timeArr=[['2024-07-01','2024-07-31'],['2024-08-01','2024-08-31'],['2024-09-01','2024-09-30'],['2024-10-01','2024-10-31'],['2024-11-01','2024-11-30']]
    # 获取当前时间戳（毫秒级别）
    current_timestamp = int(time.time() * 1000)
    # 插入当前时间戳到URL中
    for timeRange in timeArr:
        name_url = f'https://matchweb.sports.qq.com/kbs/list?from=NBA_PC&columnId=100000&startTime={timeRange[0]}&endTime={timeRange[1]}&from=sporthp&callback=ajaxExec&_={current_timestamp}'
        try:
            response = requests.get(name_url, headers=headers)
            # print(response.content)
            # print(response.text)
            store_json(response.json())
            print("爬取赛程json文件成功")
        except ConnectionError:
            print("爬取失败")
        print("sucess")
        deal_store_data()


if __name__ == '__main__':
    main()