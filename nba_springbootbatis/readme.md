## API 文档
| Abbreviation | Full Term                    |
|--------------|------------------------------|
| Rk           | Rank                         |
| G            | Games                        |
| MP           | Minutes Played               |
| FG           | Field Goals                  |
| FGA          | Field Goal Attempts          |
| FG%          | Field Goal Percentage        |
| 3P           | 3-Point Field Goals          |
| 3PA          | 3-Point Field Goal Attempts  |
| 3P%          | 3-Point Field Goal Percentage|
| 2P           | 2-Point Field Goals          |
| 2PA          | 2-Point Field Goal Attempts  |
| 2P%          | 2-Point Field Goal Percentage|
| FT           | Free Throws                  |
| FTA          | Free Throw Attempts          |
| FT%          | Free Throw Percentage        |
| ORB          | Offensive Rebounds           |
| DRB          | Defensive Rebounds           |
| TRB          | Total Rebounds               |
| AST          | Assists                      |
| STL          | Steals                       |
| BLK          | Blocks                       |
| TOV          | Turnovers                    |
| PF           | Personal Fouls               |
| PTS          | Points                       |
### 球队名称与ID对照表
```markdown
| Team Name               | Team ID |
|-------------------------|---------|
| Atlanta Hawks            | 1       |
| Boston Celtics           | 2       |
| Brooklyn Nets            | 3       |
| Charlotte Hornets        | 4       |
| Chicago Bulls            | 5       |
| Cleveland Cavaliers      | 6       |
| Dallas Mavericks         | 7       |
| Denver Nuggets           | 8       |
| Detroit Pistons          | 9       |
| Golden State Warriors    | 10      |
| Houston Rockets          | 11      |
| Indiana Pacers           | 12      |
| Los Angeles Clippers     | 13      |
| Los Angeles Lakers       | 14      |
| Memphis Grizzlies        | 15      |
| Miami Heat               | 16      |
| Milwaukee Bucks          | 17      |
| Minnesota Timberwolves   | 18      |
| New Orleans Pelicans     | 19      |
| New York Knicks          | 20      |
| Oklahoma City Thunder    | 21      |
| Orlando Magic            | 22      |
| Philadelphia 76ers       | 23      |
| Phoenix Suns             | 24      |
| Portland Trail Blazers   | 25      |
| Sacramento Kings         | 26      |
| San Antonio Spurs        | 27      |
| Toronto Raptors          | 28      |
| Utah Jazz                | 29      |
| Washington Wizards       | 30      |
```
### 获取未来 10 场比赛数据

- **URL**: `http://localhost:8888/search/next10`
- **方法**: `GET`
- **描述**: 获取未来 10 场比赛的列表。
- **返回数据示例**:

```json
[
  {
    "date": "2024-08-27",
    "leftName": "23-24赛季常规赛 独行侠vs老鹰重播",
    "leftGoal": "0",
    "rightName": "",
    "rightGoal": "0"
  }
]
```

### 获取过去 10 场比赛数据

- **URL**: `http://localhost:8888/search/last10`
- **方法**: `GET`
- **描述**: 获取过去 10 场比赛的列表。
- **返回数据示例**:

```json
[
  {
    "date": "2024-08-27",
    "leftName": "23-24赛季常规赛 独行侠vs老鹰重播",
    "leftGoal": "0",
    "rightName": "",
    "rightGoal": "0"
  }
]
```

### 返回字段说明

| 字段名称   | 类型   | 说明                             |
| ---------- | ------ | -------------------------------- |
| `date`     | String | 比赛日期                         |
| `leftName` | String | 左侧球队名称                     |
| `leftGoal` | String | 左侧球队得分                     |
| `rightName`| String | 右侧球队名称（可能为空）          |
| `rightGoal`| String | 右侧球队得分                     |

### 示例调用

#### 获取未来 10 场比赛数据
```bash
curl -X GET "http://localhost:8888/search/next10"
```

#### 获取过去 10 场比赛数据
```bash
curl -X GET "http://localhost:8888/search/last10"
```

---

## PlayerController Postman 测试案例

### 1. 获取球员信息 (`/search/player`)

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/player`

#### 请求参数：

| 参数名         | 类型   | 说明                       | 示例值        |
| -------------- | ------ | -------------------------- | ------------- |
| `player_name`  | String | 球员名称（模糊查询），可选  | `LeBron`      |
| `team_id`      | Integer| 球队ID，可选                | `1`           |
| `team_name`    | String | 球队名称（模糊查询），可选  | `Lakers`      |
| `pageNum`      | Integer| 页码，可选，默认为 `1`      | `1`           |
| `pageSize`     | Integer| 每页显示的数量，可选，默认为 `10` | `10`         |

#### 示例请求：
```bash
GET http://localhost:8888/search/player?player_name=LeBron&team_name=Lakers&pageNum=1&pageSize=10
```

#### 示例响应：
```json
{
    "meta": {
        "status": 200,
        "msg": "请求成功"
    },
    "data": {
        "pageData": [
            {
                "player_name": "LeBron James",
                "team_name": "Los Angeles Lakers",
                "games_played": "82",
                "points": "1698",
                "assists": "540",
                "total_rebounds": "650",
                "career_start_year": "2003",
                "career_end_year": null,
                "minutes_per_game": "36.5",
                "points_per_game": "21.5"
            }
        ],
        "pageNum": 1,
        "totalPage": 5,
        "totalNum": 50
    }
}
```

### 2. 获取球员信息，指定 `team_id` (`/search/player`)

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/player`

#### 请求参数：

| 参数名         | 类型   | 说明                       | 示例值   |
| -------------- | ------ | -------------------------- | -------- |
| `team_id`      | Integer| 球队ID，可选                | `1`      |
| `pageNum`      | Integer| 页码，可选，默认为 `1`      | `1`      |
| `pageSize`     | Integer| 每页显示的数量，可选，默认为 `10` | `10`    |

#### 示例请求：
```bash
GET http://localhost:8888/search/player?team_id=1&pageNum=1&pageSize=10
```

#### 示例响应：
```json
{
    "meta": {
        "status": 200,
        "msg": "请求成功"
    },
    "data": {
        "pageData": [
            {
                "player_name": "LeBron James",
                "team_name": "Los Angeles Lakers",
                "games_played": "82",
                "points": "1698",
                "assists": "540",
                "total_rebounds": "650"
            }
        ],
        "pageNum": 1,
        "totalPage": 5,
        "totalNum": 50
    }
}
```

### 3. 模糊搜索球员 (`/search/player`)

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/player`

#### 请求参数：

| 参数名         | 类型   | 说明                       | 示例值   |
| -------------- | ------ | -------------------------- | -------- |
| `player_name`  | String | 球员名称（模糊查询），可选  | `LeBron` |

#### 示例请求：
```bash
GET http://localhost:8888/search/player?player_name=LeBron
```

#### 示例响应：
```json
{
    "meta": {
        "status": 200,
        "msg": "请求成功"
    },
    "data": {
        "pageData": [
            {
                "player_name": "LeBron James",
                "team_name": "Los Angeles Lakers",
                "games_played": "82",
                "points": "1698",
                "assists": "540",
                "total_rebounds": "650"
            }
        ],
        "pageNum": 1,
        "totalPage": 1,
        "totalNum": 1
    }
}
```

### 4. 错误请求示例：请求未传递任何参数

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/player`

#### 请求参数：
无

#### 示例请求：
```bash
GET http://localhost:8888/search/player
```

#### 示例响应：
```json
{
    "meta": {
        "status": 400,
        "msg": "参数缺失：需要至少一个查询条件"
    }
}
```

---

## NbaController Postman 测试案例

### 1. 获取按指定字段排序的球队信息 (`/search/team`)

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/team`

#### 请求参数：

| 参数名         | 类型   | 说明                           | 示例值   |
| -------------- | ------ | ------------------------------ | -------- |
| `season_year`  | String | 赛季年份，可选                  | `2023`   |
| `pageNum`      | String | 页码，可选，默认为 `1`          | `1`      |
| `team_name`    | String | 球队名称（模糊查询），可选      | `Lakers` |
| `pageSize`     | String | 每页显示的数量，可选，默认为 `10` | `10`     |
| `index`        | String | 排序字段名，可选                | `points` |
| `team_id`      | String | 球队ID，可选                   | `1`      |

#### 示例请求：
```bash
GET http://localhost:8888/search/team?season_year=2023&pageNum=1&team_name=Lakers&pageSize=10&index=points
```

#### 示例响应：
```json
{
    "meta": {
        "status": 200,
        "msg": "请求成功"
    },
    "data": {
        "pageData": [
            {
                "team_id": "1",
                "team_name": "Los Angeles Lakers",
                "games_played": "82",
                "points": "9200",
                "assists": "2200",
                "total_rebounds": "3200",
                "team_location": "Los Angeles",
                "team_alias": "LAL",
                "playoff_appearances": 60,
                "championships_won": 17,
                "team_logo": "lakers_logo.png",
                "season_year": 2023
            }
        ],
        "pageNum": 1,
        "totalPage": 10,
        "totalNum": 100
    }
}
```

### 2. 获取球队排名信息 (`/search/teamRank`)

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/teamRank`

#### 请求参数：

| 参数名         | 类型   | 说明                    | 示例值   |
| -------------- | ------ | ----------------------- | -------- |
| `season_year` 

| String | 赛季年份，可选           | `2023`   |
| `team_id`      | String | 球队ID，必选             | `1`      |

#### 示例请求：
```bash
GET http://localhost:8888/search/teamRank?season_year=2023&team_id=1
```

#### 示例响应：
```json
{
    "meta": {
        "status": 200,
        "msg": "请求成功"
    },
    "data": [
        {
            "team_id": "1",
            "points_per_game": "112.2",
            "points_per_game_rank": 3,
            "rebounds_per_game": "45.1",
            "rebounds_per_game_rank": 5,
            "assists_per_game": "24.8",
            "assists_per_game_rank": 8,
            "steals_per_game": "7.5",
            "steals_per_game_rank": 12,
            "blocks_per_game": "5.3",
            "blocks_per_game_rank": 2,
            "points_per_game_max": "120.5",
            "rebounds_per_game_max": "48.6",
            "assists_per_game_max": "26.7",
            "steals_per_game_max": "9.2",
            "blocks_per_game_max": "6.0",
            "points_per_game_avg": "110.3",
            "rebounds_per_game_avg": "43.5",
            "assists_per_game_avg": "23.7",
            "steals_per_game_avg": "7.1",
            "blocks_per_game_avg": "5.1"
        }
    ]
}
```

---

### 3. 错误案例：获取按指定字段排序的球队信息（缺少必选参数 `team_id`）

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/teamRank`

#### 请求参数：

| 参数名         | 类型   | 说明         | 示例值   |
| -------------- | ------ | ------------ | -------- |
| `season_year`  | String | 赛季年份，可选 | `2023`   |
| `team_id`      | String | 缺少必选参数  | 无       |

#### 示例请求：
```bash
GET http://localhost:8888/search/teamRank?season_year=2023
```

#### 示例响应：
```json
{
    "meta": {
        "status": 400,
        "msg": "参数缺失：team_id 是必填项"
    }
}
```

### 4. 获取球队信息，按 `games_played` 排序 (`/search/team`)

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/team`

#### 请求参数：

| 参数名         | 类型   | 说明                         | 示例值  |
| -------------- | ------ | ---------------------------- | ------- |
| `season_year`  | String | 赛季年份，可选                 | `2023`  |
| `pageNum`      | String | 页码，可选，默认为 `1`         | `1`     |
| `pageSize`     | String | 每页显示数量，可选，默认为 `10` | `10`    |
| `index`        | String | 排序字段名，可选               | `games_played` |
| `team_id`      | String | 球队ID，可选                  | `2`     |

#### 示例请求：
```bash
GET http://localhost:8888/search/team?season_year=2023&pageNum=1&pageSize=10&index=games_played
```

#### 示例响应：
```json
{
    "meta": {
        "status": 200,
        "msg": "请求成功"
    },
    "data": {
        "pageData": [
            {
                "team_id": "2",
                "team_name": "Golden State Warriors",
                "games_played": "82",
                "points": "8800",
                "assists": "2500",
                "total_rebounds": "3100",
                "team_location": "San Francisco",
                "team_alias": "GSW",
                "playoff_appearances": 45,
                "championships_won": 7,
                "team_logo": "warriors_logo.png",
                "season_year": 2023
            }
        ],
        "pageNum": 1,
        "totalPage": 8,
        "totalNum": 80
    }
}
```

---

### 5. 测试无查询参数的请求 (`/search/team`)

#### 请求方式：
`GET`

#### 请求 URL：
`http://localhost:8888/search/team`

#### 请求参数：
无

#### 示例请求：
```bash
GET http://localhost:8888/search/team
```

#### 示例响应：
```json
{
    "meta": {
        "status": 200,
        "msg": "请求成功"
    },
    "data": {
        "pageData": [
            {
                "team_id": "1",
                "team_name": "Los Angeles Lakers",
                "games_played": "82",
                "points": "9200",
                "assists": "2200",
                "total_rebounds": "3200",
                "team_location": "Los Angeles",
                "team_alias": "LAL",
                "playoff_appearances": 60,
                "championships_won": 17,
                "team_logo": "lakers_logo.png",
                "season_year": 2023
            },
            {
                "team_id": "2",
                "team_name": "Golden State Warriors",
                "games_played": "82",
                "points": "8800",
                "assists": "2500",
                "total_rebounds": "3100",
                "team_location": "San Francisco",
                "team_alias": "GSW",
                "playoff_appearances": 45,
                "championships_won": 7,
                "team_logo": "warriors_logo.png",
                "season_year": 2023
            }
        ],
        "pageNum": 1,
        "totalPage": 10,
        "totalNum": 100
    }
}
```

---

### 错误处理

如果查询参数无效或出现数据库问题，API 将返回相应的错误消息。

例如：
```json
{
    "meta": {
        "status": 400,
        "msg": "参数无效"
    }
}
```

或
```json
{
    "meta": {
        "status": 500,
        "msg": "服务器内部错误"
    }
}
```
以下是基于你最新代码的 `position` 和 `season` 接口文档，包括请求路径、请求方法、请求参数和示例响应等信息。

### 1. **获取去重的 `position` 列表接口文档**

#### URL:
```
GET /search/position
```

#### 描述:
该接口用于获取去重后的球员位置（`position`）列表。

#### 请求方式:
```
GET
```

#### 请求参数:
无请求参数。

#### 响应示例:
```json
{
  "code": 200,
  "message": "Query Successful",
  "data": [
    {
      "team_id": 1,
      "season_year": 2020,
      "position": "前锋"
    },
    {
      "team_id": 2,
      "season_year": 2020,
      "position": "后卫"
    }
  ]
}
```

#### 响应字段:
| 字段                  | 类型    | 描述                        |
|-----------------------|---------|-----------------------------|
| `code`                | Integer | 响应状态码，200表示成功      |
| `message`             | String  | 响应消息                    |
| `data`                | List    | 球员位置数据列表            |
| `data.team_id`        | Integer | 球队ID                      |
| `data.season_year`    | Integer | 赛季年份                    |
| `data.position`       | String  | 球员位置                    |

#### 状态码:
- **200** - 请求成功
- **500** - 服务器内部错误

---

### 2. **获取去重的 `season_year` 列表接口文档**

#### URL:
```
GET /search/season
```

#### 描述:
该接口用于获取去重后的赛季年份（`season_year`）列表。

#### 请求方式:
```
GET
```

#### 请求参数:
无请求参数。

#### 响应示例:
```json
{
  "code": 200,
  "message": "Query Successful",
  "data": [
    {
      "team_id": 1,
      "season_year": 2020,
      "position": "前锋"
    },
    {
      "team_id": 2,
      "season_year": 2019,
      "position": "后卫"
    }
  ]
}
```

#### 响应字段:
| 字段                  | 类型    | 描述                        |
|-----------------------|---------|-----------------------------|
| `code`                | Integer | 响应状态码，200表示成功      |
| `message`             | String  | 响应消息                    |
| `data`                | List    | 赛季年份数据列表            |
| `data.team_id`        | Integer | 球队ID                      |
| `data.season_year`    | Integer | 赛季年份                    |
| `data.position`       | String  | 球员位置                    |

#### 状态码:
- **200** - 请求成功
- **500** - 服务器内部错误

---