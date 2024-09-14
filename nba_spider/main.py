from SQLiteDB import SQLiteDB
from model import ModelWrapper
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, mean_squared_error
import numpy as np
import pandas as pd
import sqlite3
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

    # # 分离特征和目标变量
    # X = df.drop('home_win', axis=1)
    # y = df['home_win']

    # # 分割数据集
    # X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)
'''
    模型训练及预测
    # 回归问题 - 使用BayesianRidge模型
    model = ModelWrapper('BayesianRidge')
    model.train(X_train, y_train)
    print("Bayesian Ridge MSE:", model.evaluate(X_test, y_test, problem_type='regression'))

    # 分类问题 - 生成分类数据并使用GaussianNB模型
    y_class = (y > np.median(y)).astype(int)  # 将连续值转换为0或1的分类标签
    X_train, X_test, y_train, y_test = train_test_split(X, y_class, test_size=0.3, random_state=42)

    model = ModelWrapper('GaussianNB')
    model.train(X_train, y_train)
    print("GaussianNB Accuracy:", model.evaluate(X_test, y_test, problem_type='classification'))
'''

'''
    也可以保存并导入模型
    # 保存模型
    joblib.dump(model, 'logistic_model.joblib')

    # 加载模型
    loaded_model = joblib.load('logistic_model.joblib')

    # 使用加载的模型进行预测
    predictions = loaded_model.predict(X_test)
    print(predictions)
'''