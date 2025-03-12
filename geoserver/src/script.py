import requests
from io import BytesIO
from PIL import Image
import os

def request_wms_image(layer_name, bbox):
    if not layer_name:
        raise ValueError("O parâmetro 'layer_name' é obrigatório.")
    if not bbox:
        raise ValueError("O parâmetro 'bbox' é obrigatório.")
    
    WMS_URL = "http://localhost:8080/geoserver/wms"
    
    params = {
        "service": "WMS",
        "version": "1.3.0",
        "request": "GetMap",
        "layers": f"{layer_name}:PB_Municipios_2023",
        "styles": "",
        "crs": "EPSG:4326",
        "bbox": bbox,
        "width": "800",
        "height": "600",
        "format": "image/png",
        "scale": "136000",
        "transparent": "true"
    }
    
    try:
        response = requests.get(WMS_URL, params=params)
        
        if response.status_code == 200:
            try:
                image = Image.open(BytesIO(response.content))
                if not os.path.exists('img'):
                    os.makedirs('img')
                image.save('src/img/mapa_esperanca.png', 'PNG')
                print("Imagem salva com sucesso em: img/mapa_esperanca.png")
            except IOError as e:
                print(f"Erro ao processar a imagem: {e}")
        else:
            print(f"Erro na requisição: {response.status_code} - {response.reason}")
    
    except requests.exceptions.RequestException as e:
        print(f"Erro ao requisitar a imagem do WMS: {e}")
    except Exception as e:
        print(f"Erro inesperado: {e}")

try:
    request_wms_image("csabrina-service", "-7.22,-36.06,-6.82,-35.66")
except ValueError as e:
    print(f"Erro de validação: {e}")
