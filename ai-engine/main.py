from fastapi import FastAPI
from pydantic import BaseModel
import uvicorn

app = FastAPI()

# Data structure jo Java se aayega
class AssetHealthData(BaseModel):
    assetName: str
    status: str
    healthScore: float

@app.post("/predict")
async def get_prediction(data: AssetHealthData):
    print(f"📩 Incoming data from Java: {data.assetName}")
    
    # Simple logic for failure prediction
    prediction = "Stable"
    if data.healthScore < 40:
        prediction = "CRITICAL: System Failure Imminent"
    elif data.healthScore < 75:
        prediction = "MODERATE: Maintenance Recommended"
        
    return {"prediction": prediction}

# Yahan space (Indentation) bahut zaroori hai!
if __name__ == "__main__":
    print("🚀 Starting AI Engine on Port 8000...")
    uvicorn.run(app, host="0.0.0.0", port=8000)