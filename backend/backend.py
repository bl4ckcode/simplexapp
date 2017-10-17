import json
import os

from flask import Flask,request,current_app
app = Flask(__name__)


@app.route('/')
def hello_world():
    return current_app.send_static_file('simpla.html')

@app.route('/submit', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        resp = ""
        string = str(request.data)
        string = string[2:len(string)-1]
        json_acceptable_string = string.replace("'", "\"")
        dict = json.loads(json_acceptable_string)
        make_file(dict)
        os.system('python3.6 simplex.py > out.txt')
        f = open('out.txt')
        content = f.readlines()
        f.close()
        resp = ''.join(str(x) for x in content)
        print(resp)
        return resp
    else:
        return 'erro LUL'

def make_file(dict):
  print(dict)
  f = open('input.txt','w')
  f.write(dict['maxmin']+' ')
  for i in range(0,len(dict['Z'])):
    f.write(dict['Z'][i]+' ')
  f.write('\n')
  numRests = int(dict['rests'])
  for i in range(0,numRests):
    for j in range(0,len(dict['Z'])+2):
      f.write(dict['R'+str(i)][j]+ ' ')
    f.write('\n')
  f.close()

if __name__ == '__main__':
  port = int(os.environ,get("PORT",5000))
  app.run(debug=True,port=port)
