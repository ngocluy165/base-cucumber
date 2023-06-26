cd `dirname $0`/..
mkdir -p tmp

cd tmp
if [ -d python-jwt-example-api ]; then
  echo "Already clone!!"
else
  echo "Clone project"
  git clone git@github.com:hungntit/python-jwt-example-api.git
fi
cd python-jwt-example-api
git pull
VENV_PATH="./"
python3 -m venv $VENV_PATH
$VENV_PATH/bin/pip install -r requirements.txt
$VENV_PATH/bin/python app.py
#
## && npm install && npm audit fix && npm run start-auth
##
#git clone https://github.com/techiediaries/fake-api-jwt-json-server.git
#cd fake-api-jwt-json-server && npm install && npm audit fix && npm run start-auth
