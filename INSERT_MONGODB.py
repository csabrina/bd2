import pymongo
import json
import os
import dotenv

dotenv.load_dotenv()

client = pymongo.MongoClient(os.getenv("URL_MONGODB"))
db = client["sample_mflix"]
def carregar_json(json_path):
    with open(json_path, 'r') as file:
        data = json.load(file)
        db["season"].insert_many(data[0]["season"])
        db["team"].insert_many(data[0]["team"])
        db["player"].insert_many(data[0]["player"])
        print("Dados iniciais carregados com sucesso!")

        
def criar_registro(collection_name, registro):
    collection = db[collection_name]
    resultado = collection.insert_one(registro)
    print(f"Registro inserido na coleção '{collection_name}' com ID: {resultado.inserted_id}")

def ler_registros(collection_name, filtro=None):
    collection = db[collection_name]
    registros = collection.find(filtro or {})
    print(f"Registros na coleção '{collection_name}':")
    for registro in registros:
        print(registro)

def atualizar_registro(collection_name, filtro, novos_dados):
    collection = db[collection_name]
    resultado = collection.update_one(filtro, {"$set": novos_dados})
    if resultado.matched_count > 0:
        print(f"Registro na coleção '{collection_name}' atualizado com sucesso!")
    else:
        print("Nenhum registro encontrado para atualizar.")

def deletar_registro(collection_name, filtro):
    collection = db[collection_name]
    resultado = collection.delete_one(filtro)
    if resultado.deleted_count > 0:
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
    carregar_json("results.json")
    criar_registro("team", new_team)
    ler_registros("team", {"city": "João Pessoa"}) 
    atualizar_registro("team", {"id": 1989}, {"nickname": "Warrior"})
    deletar_registro("team", {"id": 1989})