from SQLiteDB import SQLiteDB
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, mean_squared_error
import numpy as np
import pandas as pd
if __name__ == "__main__":
    # 初始化数据库连接
    db = SQLiteDB('nba_springbootbatis/springbootbatis/src/main/resources/nba_total.db')

    # 连接数据库
    db.connect()

    # 查询比赛数据
    query = '''
        SELECT date, leftName, leftGoal, rightName, rightGoal
        FROM nba_schedule
        WHERE leftGoal = 0 AND rightGoal = 0
          AND leftName != '' AND rightName != ''
          AND leftName != '中国男篮' AND rightName != '中国男篮'
        ORDER BY date
    '''
    games = db.execute_query(query)

    for game in games:
        print(game)

    # 断开连接
    db.disconnect()

    df = pd.DataFrame(games)


