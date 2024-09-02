import pymysql

class MySQLDatabase:
    def __init__(self, host, port, user, password, database):
        self.host = host
        self.port = port
        self.user = user
        self.password = password
        self.database = database
        self.connection = None
        self.cursor = None

    def connect(self):
        """
        建立数据库连接
        """
        try:
            self.connection = pymysql.connect(
                host=self.host,
                port=self.port,
                user=self.user,
                password=self.password,
                database=self.database
            )
            self.cursor = self.connection.cursor()
            print("Successfully connected to the database.")
        except pymysql.MySQLError as e:
            print(f"Error connecting to MySQL Database: {e}")
            self.connection = None
            self.cursor = None

    def execute_query(self, query):
        """
        执行SQL查询并返回结果
        """
        if self.connection and self.cursor:
            try:
                self.cursor.execute(query)
                return self.cursor.fetchall()
            except pymysql.MySQLError as e:
                print(f"Error executing query: {e}")
                return None
        else:
            print("No active database connection.")
            return None

    def close(self):
        """
        关闭数据库连接
        """
        if self.cursor:
            self.cursor.close()
        if self.connection:
            self.connection.close()
        print("Database connection closed.")