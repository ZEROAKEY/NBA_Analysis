import requests
from bs4 import BeautifulSoup
import pandas as pd
import sqlite3
import re
import time
import random

def getTeamUrl(teamArr):
    # 设置请求头
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }
    # 目标网址
    url = 'https://www.basketball-reference.com/teams/'

    # 发送GET请求
    response = requests.get(url, headers=headers)

    # 检查请求是否成功
    if response.status_code == 200:
        # 使用BeautifulSoup解析HTML内容
        soup = BeautifulSoup(response.text, 'html.parser')

        # 找到Active Franchises表格
        table = soup.find('table', {'id': 'teams_active'})

        # 提取表中的队伍链接
        if table:
            # 获取表中的所有队伍链接
            rows = table.find('tbody').find_all('tr')
            for row in rows:
                # 找到队伍链接所在的列
                link = row.find('a')
                if link and 'href' in link.attrs:
                    # 提取href并存入teamArr列表
                    team_link = link['href']
                    teamArr.append(team_link)

        # 输出teamArr
        print(teamArr)
    else:
        print(f'请求失败，状态码: {response.status_code}')


def getTeamInfo(team, cursor, conn):
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }
    url = f'https://www.basketball-reference.com{team}players.html'

    # 随机延迟避免触发反爬虫机制
    time.sleep(random.uniform(3, 7))  # 增加随机延迟，间隔 3 到 7 秒

    # 发送GET请求
    response = requests.get(url, headers=headers)

    # 检查请求是否成功
    if response.status_code == 200:
        # 使用BeautifulSoup解析HTML内容
        soup = BeautifulSoup(response.text, 'html.parser')

        # 1. 提取球队简介信息
        team_info_section = soup.find('div', {'id': 'meta'})  # 球队信息部分
        if team_info_section:
            # 查找球队名称
            team_name = team_info_section.find('h1').text.strip().split('\n')[0]

            # 提取具体信息
            team_info_text = team_info_section.text.strip()

            # 正则表达式匹配相关信息
            location = re.search(r'Location:\s+(.+)', team_info_text).group(1)
            team_names_match = re.search(r'Team Names:\s+(.+)', team_info_text)
            team_names = team_names_match.group(1) if team_names_match else team_name
            seasons = re.search(r'Seasons:\s+(.+);', team_info_text).group(1)
            record = re.search(r'Record:\s+([\d-]+,\s\.\d+ W-L%)', team_info_text).group(1)
            playoff_appearances = int(re.search(r'Playoff Appearances:\s+(\d+)', team_info_text).group(1))
            championships = int(re.search(r'Championships:\s+(\d+)', team_info_text).group(1))
            team_logo = soup.find('img', {'class': 'teamlogo'})['src']

            # 组织为字典格式
            team_data = {
                "Name": team_name,
                "Location": location,
                "Team Names": team_names,
                "Seasons": seasons,
                "Record": record,
                "Playoff Appearances": playoff_appearances,
                "Championships": championships,
                "Logo": team_logo
            }

            # 输出字典格式
            print(team_data)

            # 将 team_data 插入到数据库
            cursor.execute('''INSERT INTO team_info 
                              (Name, Location, Team_Names, Seasons, Record, Playoff_Appearances, Championships, Logo) 
                              VALUES (?, ?, ?, ?, ?, ?, ?, ?)''',
                           (team_data["Name"], team_data["Location"], team_data["Team Names"], team_data["Seasons"],
                            team_data["Record"], team_data["Playoff Appearances"], team_data["Championships"],
                            team_data["Logo"]))

        else:
            print("没有找到球队简介")

        # 2. 提取表格中的球员信息
        table = soup.find('table', {'id': 'franchise_register'})

        # 初始化数据存储
        headers = []
        rows = []

        # 提取表头
        if table:
            header_row = table.find('thead').find_all('tr')[-1]  # 选择最后一行作为表头
            headers = [th.text.strip() for th in header_row.find_all('th')]

            # 获取表中的数据行
            table_rows = table.find('tbody').find_all('tr')
            for tr in table_rows:
                # 提取 Rk 列，如果存在
                rk = tr.find('th').text.strip() if tr.find('th') else None
                row = [td.text.strip() for td in tr.find_all('td')]

                # 将 Rk 列添加到 row 的第一个元素
                if rk:
                    row.insert(0, rk)

                # 如果当前行的列数小于表头列数，则填充 None
                if len(row) < len(headers):
                    row.extend([None] * (len(headers) - len(row)))

                # 确保每一行的数据与表头列数一致
                if len(row) == len(headers):
                    rows.append(row)

            # 使用 pandas 将数据转换为 DataFrame
            df = pd.DataFrame(rows, columns=headers)

            # 处理重复的列名（如 MP），为重复列名添加后缀
            df.columns = pd.io.parsers.ParserBase({'names': df.columns})._maybe_dedup_names(df.columns)

            # 删除所有列全为空的行
            df = df.dropna(how='all')

            # 删除只有 'Rk' 列有数据的行，即除了 'Rk' 列外，其他列均为空的行
            df = df.dropna(subset=headers[1:], how='all')

            # 添加一列 'TeamName'
            df['TeamName'] = team_name

            print(f"球员信息 DataFrame ({team_name}):\n", df)
            # 保存到csv
            df.to_csv(f'NBA_{team_name}_players_info.csv', index=False)
            # 保存到 SQLite 数据库中
            df.to_sql('players_info', conn, if_exists='append', index=False)

        else:
            print("没有找到球员信息表格")

    else:
        print(f'请求失败，状态码: {response.status_code}')


# 主流程
teamArr = []
getTeamUrl(teamArr)

# 创建 SQLite 数据库连接（在循环外部）
conn = sqlite3.connect('nba_total.db')
cursor = conn.cursor()

# 创建存储球队信息的表（在循环外部，只需创建一次）
cursor.execute('''INSERT INTO team_info 
                  (team_name, team_location, team_alias, seasons, record, playoff_appearances, championships_won, team_logo) 
                  VALUES (?, ?, ?, ?, ?, ?, ?, ?)''',
               (team_data["Name"], team_data["Location"], team_data["Team Names"], team_data["Seasons"],
                team_data["Record"], team_data["Playoff Appearances"], team_data["Championships"], team_data["Logo"]))


# 循环获取每个队伍的信息
for team in teamArr:
    getTeamInfo(team, cursor, conn)

# 提交更改并关闭数据库连接（在循环结束后关闭）
conn.commit()
conn.close()
