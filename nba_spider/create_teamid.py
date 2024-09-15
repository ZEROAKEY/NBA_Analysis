import sqlite3

# 连接到 SQLite 数据库
conn = sqlite3.connect('nba_total.db')
cursor = conn.cursor()

# 获取所有表名
cursor.execute("SELECT name FROM sqlite_master WHERE type='table';")
tables = cursor.fetchall()

# 从 team_names 表中获取 team_name 和 team_id 映射
cursor.execute("SELECT team_name, team_id FROM team_names")
team_name_id_map = {row[0]: row[1] for row in cursor.fetchall()}

# 遍历所有表，检查是否存在 team_name 列
for table in tables:
    table_name = table[0]

    # 获取表的所有列名
    cursor.execute(f"PRAGMA table_info({table_name})")
    columns = [col[1] for col in cursor.fetchall()]

    # 如果表中存在 team_name 列
    if 'team_name' in columns:
        print(f"表 {table_name} 存在 team_name 列，开始更新 team_id 列")

        # 更新表中的 team_id 列
        for team_name, team_id in team_name_id_map.items():
            cursor.execute(f"""
                UPDATE {table_name}
                SET team_id = ?
                WHERE team_name = ?
            """, (team_id, team_name))

# 提交更改并关闭数据库连接
conn.commit()
conn.close()

print("所有表中的 team_id 已成功更新")
