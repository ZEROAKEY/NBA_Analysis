from sklearn.naive_bayes import GaussianNB
from sklearn.linear_model import LinearRegression, BayesianRidge
from sklearn.metrics import accuracy_score, mean_squared_error
import numpy as np

class ModelWrapper:
    def __init__(self, model_name, **model_params):
        # 创建模型实例
        self.model = self._initialize_model(model_name, **model_params)

    def _initialize_model(self, model_name, **model_params):
        """
        根据提供的模型名称和参数初始化模型。
        """
        models = {
            'GaussianNB': GaussianNB,
            'LinearRegression': LinearRegression,
            'BayesianRidge': BayesianRidge
        }
        
        if model_name not in models:
            raise ValueError(f"Model {model_name} is not supported.")
        
        return models[model_name](**model_params)

    def train(self, X_train, y_train):
        """
        训练模型。
        """
        self.model.fit(X_train, y_train)

    def predict(self, X_test):
        """
        使用模型进行预测。
        """
        return self.model.predict(X_test)

    def evaluate(self, X_test, y_test, problem_type='classification'):
        """
        评估模型，支持分类和回归。
        """
        y_pred = self.predict(X_test)
        
        if problem_type == 'classification':
            return accuracy_score(y_test, y_pred)
        elif problem_type == 'regression':
            return mean_squared_error(y_test, y_pred)
        else:
            raise ValueError("Problem type must be 'classification' or 'regression'.")
    
