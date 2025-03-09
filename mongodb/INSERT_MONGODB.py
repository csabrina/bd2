import pymongo
import json
import os
import dotenv

dotenv.load_dotenv()

client = pymongo.MongoClient(os.getenv("URL_MONGODB"))
db = client["sample_mflix"]

def load_json(json_path):
    with open(json_path, 'r') as file:
        data = json.load(file)
        db["season"].insert_many(data[0]["season"])
        db["team"].insert_many(data[0]["team"])
        db["player"].insert_many(data[0]["player"])
        print("Dados iniciais carregados com sucesso!")

def create_record(collection_name, record):
    collection = db[collection_name]
    result = collection.insert_one(record)
    print(f"Registro inserido na coleção '{collection_name}' com ID: {result.inserted_id}")

def read_records(collection_name, filter=None):
    collection = db[collection_name]
    records = collection.find(filter or {})
    print(f"Registros na coleção '{collection_name}':")
    for record in records:
        print(record)

def update_record(collection_name, filter, new_data):
    collection = db[collection_name]
    result = collection.update_one(filter, {"$set": new_data})
    if result.matched_count > 0:
        print(f"Registro na coleção '{collection_name}' atualizado com sucesso!")
    else:
        print("Nenhum registro encontrado para atualizar.")

def delete_record(collection_name, filter):
    collection = db[collection_name]
    result = collection.delete_one(filter)
    if result.deleted_count > 0:
        print(f"Registro removido da coleção '{collection_name}' com sucesso!")
    else:
        print("Nenhum registro encontrado para deletar.")

if __name__ == "__main__":
    new_team = {
        "id": 1989,
        "full_name": "João Pessoa Warriors",
        "abbreviation": "JPW",
        "nickname": "Warriors",
        "city": "João Pessoa",
        "state": "Paraíba",
        "year_founded": 2021
    }
    load_json("results.json")
    create_record("team", new_team)
    read_records("team", {"city": "João Pessoa"}) 
    update_record("team", {"id": 1989}, {"nickname": "Warrior"})
    delete_record("team", {"id": 1989})
