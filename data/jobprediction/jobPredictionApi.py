from flask import Flask, request, jsonify
from flask_cors import CORS

from data.jobprediction.model.jobPredictionModel import predictJob

app = Flask(__name__)
CORS(app)

@app.route('/api/predict-job', methods=['POST'])
def predict_job():
    # *Get Data* 
    data = request.get_json()
    
    # *Prediction using the loaded model*
    prediction = predictJob(data)
    
    # *Return the prediction*
    return jsonify({'job': prediction})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)