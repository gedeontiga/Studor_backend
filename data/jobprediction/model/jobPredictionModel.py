import pandas as pd
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score
from joblib import dump, load

# *Load data*
data = pd.read_csv('data/jobprediction/dataset/job_prediction_dataset.csv')

# *Categorical variables encoding*
labelEncoderOption = LabelEncoder()
data['option'] = labelEncoderOption.fit_transform(data['option'])
labelEncoderLoisir1 = LabelEncoder()
data['loisir_1'] = labelEncoderLoisir1.fit_transform(data['loisir_1'])
labelEncoderLoisir2 = LabelEncoder()
data['loisir_2'] = labelEncoderLoisir2.fit_transform(data['loisir_2'])
labelEncoderMetier = LabelEncoder()
data['metier'] = labelEncoderMetier.fit_transform(data['metier'])

# *Splitting the dataset into features and target variable*
X = data[['note_1', 'note_2', 'note_3', 'note_4', 'note_5', 'moyenne', 'option', 'age', 'loisir_1', 'loisir_2']]
y = data['metier']

# *Feature scaling*
scaler = StandardScaler()
X = scaler.fit_transform(X)

# *Splitting the dataset into training set and test set*
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.1, random_state=42)

# *Decision tree classifier*
dtModel = DecisionTreeClassifier(max_depth=10, min_samples_leaf=2, random_state=42)
dtModel.fit(X_train, y_train)

# *Prediction and rate*
y_pred = dtModel.predict(X_test)
print(f"Accuracy: {accuracy_score(y_test, y_pred) * 100:.2f}%")

# *Cross-validation*
scores = cross_val_score(dtModel, X_train, y_train, cv=5)
print(f"Cross-validation scores: {scores.mean() * 100:.2f}%")
dump(dtModel, 'data/jobprediction/model/export/decision_tree_model_job_prediction.pkl')

# *Load model file*
loadedModel = load('data/jobprediction/model/export/decision_tree_model_job_prediction.pkl')

def predictJob(newData):
    new_data = pd.DataFrame(newData, columns=['note_1', 'note_2', 'note_3', 'note_4', 'note_5', 'moyenne', 'option', 'age', 'loisir_1', 'loisir_2'])
    new_data['option'] = labelEncoderOption.transform(new_data['option'])
    new_data['loisir_1'] = labelEncoderLoisir1.transform(new_data['loisir_1'])
    new_data['loisir_2'] = labelEncoderLoisir2.transform(new_data['loisir_2'])
    new_data = scaler.transform(new_data)
    prediction = loadedModel.predict(new_data)
    return labelEncoderMetier.inverse_transform(prediction)[0]