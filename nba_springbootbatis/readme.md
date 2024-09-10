## API 文档

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

### 错误响应

- **HTTP 状态码**: `404 Not Found`
- **返回内容**: 错误信息

### 示例调用

#### 获取未来 10 场比赛数据
```bash
curl -X GET "http://localhost:8888/search/next10"
```

#### 获取过去 10 场比赛数据
```bash
curl -X GET "http://localhost:8888/search/last10"
```