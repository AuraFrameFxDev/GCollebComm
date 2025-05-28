import firebase_admin
import os
from fastapi import FastAPI, HTTPException, Depends
from fastapi.middleware.cors import CORSMiddleware
from firebase_admin import credentials, firestore, auth, storage

SERVICE_ACCOUNT_KEY_PATH = "server/credentials/service-account.json"

try:
    cred = credentials.Certificate(SERVICE_ACCOUNT_KEY_PATH)
    firebase_admin.initialize_app(cred, {
        'databaseURL': 'https://your-project.firebaseio.com',
        'storageBucket': 'your-project.appspot.com'
    })
    db = firestore.client()
except Exception as e:
    print(f"Error initializing Firebase Admin SDK: {e}")

analytics_service = firebase_admin.analytics

app = FastAPI(title="Genesis AI Backend")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:8081", "https://your-domain.com"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get("/")
def read_root():
    return {
        "status": "success",
        "message": "Genesis AI Backend is running!",
        "version": "1.0.0",
        "firebase": {
            "status": "initialized" if firebase_admin._apps else "not initialized",
            "database": "firestore" if firebase_admin._apps else "N/A"
        }
    }


@app.get("/health")
def health_check():
    return {"status": "healthy"}


@app.get("/firebase/auth/{token}")
async def verify_token(token: str):
    try:
        if not firebase_admin._apps:
            raise HTTPException(status_code=500, detail="Firebase Admin SDK not initialized.")
        decoded_token = auth.verify_id_token(token)
        return {"valid": True, "user_id": decoded_token["uid"]}
    except Exception as e:
        raise HTTPException(status_code=401, detail=str(e))


if __name__ == "__main__":
    import uvicorn

    uvicorn.run("app:app", host="0.0.0.0", port=8000, reload=True)
