import re
import urllib2
from bs4 import BeautifulSoup, NavigableString, Tag
from xml.etree import ElementTree
import requests


def fbFeedNames():
    
    with open("fbFeed.txt", "r") as f:

        #read the file
        fdata = f.read()
        print(fdata[0:30])


    bsinput = BeautifulSoup(fdata, "html.parser")
    for br in bsinput.findAll('br'):
        next = br.nextSibling
        if not (next and isinstance(next,NavigableString)):
            continue
        next2 = next.nextSibling
        if next2 and isinstance(next2,Tag) and next2.name == 'br':
            text = str(next).strip()
            if text:
                print "Found:", next

def test():
        i = 112304
        i = str(i)
        print i[3:]

def gregFunc():
    for i in range(401, 120000):
        try:
                folder = int("".join(list(str(i))[:-3]))
        except ValueError:
                folder = 0
        h4x0r_header = {"Referer": "http://dogs.doglost.co.uk"}
        with open('images/%i.jpg' % i, 'wb') as f:
                #print ('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, i), headers=h4x0r_header))
                f.write(requests.get('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, i), headers=h4x0r_header).content)




def dogLostFeedNames():

    #read url
    response = urllib2.urlopen('http://www.doglost.co.uk/dog-search.php?status=Lost')
    response1 = ""
    while(1):
        data = response.read()
        if not data:
            break
        response1 += data
    if(response == False):
        print("NO INTERNET CONNECTION")
    """print response.info()"""
    dogLostFeed = response1

    #parse
    bsinput = BeautifulSoup(dogLostFeed, "html.parser")
    trLost = (bsinput.find_all('tr', {"class" : "lost"}))
    trStolen = (bsinput.find_all('tr', {"class" : "stolen"}))
    i = 0
    tdLostList = []
    tdStolenList = []
    while(i < len(trLost)):
        tdLost = trLost[i].find_all('td', {"align" : "left"})
        tdLostList.append(tdLost[0].find_all('a')[0].string)
        i += 1

    i = 0
    while(i < len(trStolen)):
        tdStolen = trStolen[i].find_all('td', {"align" : "left"})
        tdStolenList.append(tdStolen[0].find_all('a')[0].string)
        i += 1

    """print(tdLostList)"""
    """print(tdStolenList)"""



    #TABLE
    HTMLtable = (bsinput.find_all('table'))

    soup = bsinput
    table = soup.find("table")
    

    # The first tr contains the field names.    
    headings = [th.get_text() for th in table.find("tr").find_all("th")]

    datasets = []
    for row in table.find_all("tr")[1:]:
        dataset = zip(headings, (td.get_text() for td in row.find_all("td")))
        datasets.append(dataset)

    """print datasets"""

    for dataset in datasets:
        for field in dataset:
            with open("dogsLostTable.txt", "a") as f:
                f.writelines("\n{0:<16}: {1}".format(field[0],field[1]))
            print("{0:<16}: {1}".format(field[0],field[1]))

    
    for li in bsinput.findAll('li'):
        next = li.nextSibling
        if not (next and isinstance(next,NavigableString)):
            continue
        next2 = next.nextSibling
        if next2 and isinstance(next2,Tag) and next2.name == 'li':
            text = str(next).strip()
            if text:
                print "Found:", next
