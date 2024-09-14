import sqlite3

class SQLiteDB:
    def __init__(self, db_path):
        """
        初始化数据库连接
        :param db_path: SQLite 数据库的路径
        """
        self.db_path = db_path
        self.connection = None
        self.cursor = None

    def connect(self):
        """
        连接到 SQLite 数据库
        """
        try:
            self.connection = sqlite3.connect(self.db_path)
            self.cursor = self.connection.cursor()
            print(f"Connected to SQLite database at {self.db_path}")
        except sqlite3.Error as e:
            print(f"Failed to connect to the database: {e}")
            raise

    def disconnect(self):
        """
        关闭数据库连接
        """
        if self.cursor:
            self.cursor.close()
        if self.connection:
            self.connection.close()
        print("Disconnected from SQLite database.")

    def execute_query(self, query, params=None):
        """
        执行查询并返回结果
        :param query: SQL 查询语句
        :param params: 查询参数 (如果有)
        :return: 查询结果
        """
        try:
            if params:
                self.cursor.execute(query, params)
            else:
                self.cursor.execute(query)
            return self.cursor.fetchall()
        except sqlite3.Error as e:
            print(f"An error occurred while executing the query: {e}")
            return []

    def check_version(self):
        """
        检查 SQLite 版本
        """
        query = 'SELECT SQLITE_VERSION()'
        result = self.execute_query(query)
        if result:
            print(f"SQLite version: {result[0][0]}")
        else:
            print("Failed to retrieve SQLite version.")

    def commit(self):
        """
        提交事务
        """
        if self.connection:
            self.connection.commit()


# 使用示例
if __name__ == "__main__":
    # 初始化类，指定 SQLite 数据库路径
    db_path = 'nba_springbootbatis/springbootbatis/src/main/resources/nba_total.db'
    db = SQLiteDB(db_path)
    
    # 连接数据库
    db.connect()
    
    # 检查数据库版本
    db.check_version()

    # 断开连接
 
