from connectDB import MySQLDatabase
from model import ModelWrapper
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, mean_squared_error
import numpy as np
import pandas as pd
import joblib
if __name__ == "__main__":
    # 数据库连接参数
    db_host = 'your-cloud-db-host'  # 云数据库主机地址
    db_port = 3306  # 端口，通常MySQL默认是3306
    db_user = 'your-username'  # 数据库用户名
    db_password = 'your-password'  # 数据库密码
    db_name = 'your-database-name'  # 数据库名称

    # 创建数据库对象
    db = MySQLDatabase(host=db_host, port=db_port, user=db_user, password=db_password, database=db_name)

    # 连接到数据库
    db.connect()

    # 执行查询并获取结果
    query = "SELECT * FROM nba_total"
    results = db.execute_query(query)

    # 输出结果
    if results:
        for row in results:
            print(row)
    # 关闭数据库连接
    db.close()

    df = pd.DataFrame(results)

    # 分离特征和目标变量
    X = df.drop('home_win', axis=1)
    y = df['home_win']

    # 分割数据集
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)
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