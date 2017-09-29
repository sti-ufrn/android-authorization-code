Esta API tem como propósito auxiliar os desenvolvedores Android a fazer uma autenticação do tipo Authorization Code na API.sistemas (https://api.ufrn.br/) da UFRN. Para usar o projeto basta importar o projeto no gradle
```gradle
compile 'sinfo.ufrn.br:JApi:1.0.0'
```
Após importar o projeto é necessário inserir o código abaixo no layout da aplicação.
```xml
<sinfo.ufrn.br.japi.JApiWebView
android:id="@+id/japiwebview"
android:layout_width="match_parent"
android:layout_height="match_parent" />
```
O próximo passo é criar uma instância JApiWebView do componente inserido no layout xml. É necessário passar, na função loadJapiWebView, a URL Base, Client ID e Client Secret. É necessáriu também informar a Activity que deverá ser exibida após a tela de login. 
```java
JApiWebView japiWebView = (JApiWebView) findViewById(R.id.japiwebview);
japiWebView.loadJapiWebView("URL_BASE", "CLIENT_ID", "CLIENT_SECRET", this, ResultActivity.class);
```
Para recuperar as informação do Token você pode usar os seguintes códigos:
```java
// Pegar o Access Token
JApi.getAccessToken(context);
// Pegar o Refresh Token
JApi.getRefreshToken(context);
// Pegar o Expires In
JApi.getExpiresIn(context);
// Pegar o Token Type 
JApi.getTokenType(context);
```
Para deslogar 
```java
JApi.deslogar();
```
Para entender melhor como funcionar o Authorization Code da API.sistema veja o passo a passo abaixo:

1 - O usuário inicia a interação com a aplicação;

2 - A aplicação faz uma requisição GET ao authorization server através da URL http://apitestes.info.ufrn.br/authz-server/oauth/authorize, passando os parâmetros client_id, response_type e redirect_uri como QueryParam; 
Ex: GET http://apitestes.info.ufrn.br/authz-server/oauth/authorize?client_id=AppId&response_type=code&redirect_uri=http://enderecoapp.com.br/pagina

3 - O usuário é redirecionado para o authorization server. Na página de autenticação exibida, deve informar suas credenciais (username, password) e, em seguida, informar se autoriza que a aplicação utilize seus dados. Para garantir a segurança das informações dos usuários, a página de autenticação exibida é a do servidor de autenticação da Superintendência de Informática (SINFO/UFRN). Além disso, vale ressaltar que são os usuários que realizam a autorização do uso dos dados disponibilizados pelos serviços da API;

4 - O authorization server retorna o código de autorização para a aplicação;

5 - Em posse desse código, a aplicação pode usá-lo para obter um access_token para o usuário. Desse modo, ela realiza uma nova requisição, que neste caso é do tipo POST, ao authorization_server através da URL http://apitestes.info.ufrn.br/authz-server/oauth/token, passando os parâmetros client_id, client_secret, redirect_uri, grant_type e code como QueryParam;
Ex: POST http://apitestes.info.ufrn.br/authz-server/oauth/token?client_id=AppId&client_secret=AppSecret&redirect_uri=http://enderecoapp.com.br/pagina&grant_type=authorization_code&code=code

6 - O authorization server retorna à aplicação um JSON contendo o access_token, token_type, refresh_token, expires_in e scope;
Ex: { "access_token": "111", "token_type": "bearer", "refresh_token": "abcd", "expires_in": 7431095, "scope": "read" }

7 - Em posse dessas informações, a aplicação já pode acessar os dados disponibilizados pela API passando o token através do parâmetro Authorization no header da requisição desejada;
Ex: GET http://apitestes.info.ufrn.br/ensino-services/services/consulta/listavinculos/usuario
Authorization: Bearer 111

8 - Os dados da API são retornados para a aplicação.
Ex.:
```json
GET 
[
	{
		"idTelefone": 0,
		"localizacao": "string",
		"setor": "string",
		"descricao": "string",
		"numero": "string",
		"ramais": [
			{
				"numero": "string",
				"descricao": "string"
			}
		]
	}
]
```
![alt text](https://api.ufrn.br/images/authorization_code_ufrn.png)

## License

```
Copyright 2013 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
