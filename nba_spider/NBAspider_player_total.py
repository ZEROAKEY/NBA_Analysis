import requests
from bs4 import BeautifulSoup
import pandas as pd
import sqlite3

# 目标网址
def get_html(url, year, conn):
    # 发送GET请求
    response = requests.get(url)

    # 检查请求是否成功
    if response.status_code == 200:
        # 使用BeautifulSoup解析HTML内容
        soup = BeautifulSoup(response.text, 'html.parser')

        # 找到 Player Totals 的表格
        table = soup.find('table', {'id': 'totals_stats'})  # 修改表格ID为 'totals_stats'

        # 初始化数据存储
        headers = []
        rows = []

        # 提取表头
        if table:
            # 获取表头
            header_row = table.find('thead').find_all('tr')[0]
            headers = [th.text for th in header_row.find_all('th')][1:]  # 跳过第一个空的 th

            # 获取表中的数据行
            table_rows = table.find('tbody').find_all('tr', class_=lambda x: x != 'thead')  # 排除可能的thead行
            for tr in table_rows:
                row = [td.text for td in tr.find_all('td')]
                if row:  # 跳过空行
                    rows.append(row)

            # 使用 pandas 将数据转换为 DataFrame
            df = pd.DataFrame(rows, columns=headers)
            df['Year'] = year  # 添加年份列

            # 重命名 DataFrame 列，匹配 SQLite 数据库中的列名
            df = df.rename(columns={
                'Player': 'player_name',
                'Pos': 'position',
                'Age': 'age',
                'Tm': 'team_name',
                'G': 'games_played',
                'GS': 'games_started',
                'MP': 'minutes_played',
                'FG': 'field_goals',
                'FGA': 'field_goals_attempted',
                'FG%': 'field_goal_pct',
                '3P': 'three_pointer',
                '3PA': 'three_pointer_attempted',
                '3P%': 'three_pointer_pct',
                '2P': 'two_pointer',
                '2PA': 'two_pointer_attempted',
                '2P%': 'two_pointer_pct',
                'eFG%': 'effective_field_goal_pct',  # 匹配数据库中的 effective_field_goal_pct
                'FT': 'free_throws',
                'FTA': 'free_throws_attempted',
                'FT%': 'free_throw_pct',
                'ORB': 'offensive_rebounds',
                'DRB': 'defensive_rebounds',
                'TRB': 'total_rebounds',
                'AST': 'assists',
                'STL': 'steals',
                'BLK': 'blocks',
                'TOV': 'turnovers',
                'PF': 'personal_fouls',
                'PTS': 'points'
            })

            # 显示数据表
            print(f"爬取 {year} 年数据成功!")
            print(df)

            # 保存到 CSV 文件
            df.to_csv(f'NBA_{year}_players_stats.csv', index=False)

            # 保存到 SQLite 数据库中
            df.to_sql('nba_players_stats', conn, if_exists='append', index=False)

        else:
            print(f'没有找到 {year} 年的 Player Totals 表格')
    else:
        print(f'请求失败，状态码: {response.status_code}')


# 创建 SQLite 数据库连接
conn = sqlite3.connect('nba_total.db')

# 爬取多个年份的数据并保存到数据库
year_list = [2020, 2021, 2022, 2023, 2024]
for year in year_list:
    url = f'https://www.basketball-reference.com/leagues/NBA_{year}_totals.html'
    get_html(url, year, conn)

# 关闭数据库连接
conn.close()
