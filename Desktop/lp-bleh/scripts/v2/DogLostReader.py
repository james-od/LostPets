import re
import urllib2
from bs4 import BeautifulSoup, NavigableString, Tag
from xml.etree import ElementTree
import requests

def readFromFirst20PagesOnDogLostWebsite():
    print "Beginning DogLost Reader"

    clearDogsLostTableTextFileFirst()

    pageNumber = 1
    while(pageNumber <= 20):

        print "Reading from page" + str(pageNumber)

        dogLostFeed = makeCallTodogLostWithPageNumber(pageNumber)

        bsinput = BeautifulSoup(dogLostFeed, "html.parser")

        parseResponseIntoLostAndStolenLists(dogLostFeed, bsinput)

        datasets = parseTableFromResponseIntoDataset(bsinput)

        storeDataSetInTextFile(datasets)

        #dontKnowWhatThisDoes(bsinput)
        
        pageNumber += 1
    print "Finished DogLost Reader"

def clearDogsLostTableTextFileFirst():
    open("dogsLostTable.txt", "w")

def makeCallTodogLostWithPageNumber(pageNumber):
    response = urllib2.urlopen('http://www.doglost.co.uk/dog-search.php?status=Lost&page=' + str(pageNumber))
    dogLostFeed = ""
    while(1):
        data = response.read()
        if not data:
            break
        dogLostFeed += data
    if(response == False):
        print("NO INTERNET CONNECTION")

    return dogLostFeed

#Future proofing I think
def parseResponseIntoLostAndStolenLists(dogLostFeed, bsinput):
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

def parseTableFromResponseIntoDataset(bsinput):
    HTMLtable = (bsinput.find_all('table'))

    soup = bsinput
    table = soup.find("table")
    

    # The first tr contains the field names.    
    headings = [th.get_text() for th in table.find("tr").find_all("th")]

    datasets = []
    for row in table.find_all("tr")[1:]:
        dataset = zip(headings, (td.get_text() for td in row.find_all("td")))
        datasets.append(dataset)

    return datasets

def storeDataSetInTextFile(datasets):
    for dataset in datasets:
        for field in dataset:
            with open("dogsLostTable.txt", "a") as f:
                f.writelines("\n{0:<16}: {1}".format(field[0],field[1]))
            #print("{0:<16}: {1}".format(field[0],field[1]))

def dontKnowWhatThisDoes(bsinput):
    for li in bsinput.findAll('li'):
        next = li.nextSibling
    if not (next and isinstance(next,NavigableString)):
        pass
    next2 = next.nextSibling
    if next2 and isinstance(next2,Tag) and next2.name == 'li':
        text = str(next).strip()
        if text:
            print "Found:", next
