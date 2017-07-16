#DogsLostInserter

#Server Connection to MySQL:

#!/usr/bin/python
import requests

import MySQLdb
"""
>>> import requests
>>> url = 'https://maps.googleapis.com/maps/api/geocode/json'
>>> params = {'sensor': 'false', 'address': 'Mountain View, CA'}
>>> r = requests.get(url, params=params)
>>> results = r.json()['results']
>>> location = results[0]['geometry']['location']
>>> location['lat'], location['lng']
(37.3860517, -122.0838511)
"""
import geocoder
"""
>>> import geocoder
>>> g = geocoder.google('Mountain View, CA')
>>> g.latlng
(37.3860517, -122.0838511)
"""

def insertDataIntoDogLostFeedStoreTable():
    print "Beginning DogLost inserter"
    conn = MySQLdb.connect(host= "mysql.james-odonnell.com",
                      port=3306,
                      user="secondguest",
                      passwd="secondGuestPW",
                      db="james_odonnell_com_lostpets_db")
    x = conn.cursor()



    DogLostFeed1 = open("dogsLostTable.txt")

    try:
        print "Deleting all in DogLostFeedStore"
        x.execute("""DELETE FROM `DogLostFeedStore` WHERE 1""")
        conn.commit()
    except:
        print("Failed to delete all from DogLostFeedStore")
        conn.rollback()


    lineNumber=1
    print "on page 1"
    for line in DogLostFeed1:

        #prints roughly the current page number
        #each info block on the text file is 10 
        #lines long and 50 per page on dogslostuk
        if(lineNumber % 500 == 0):
            print "on page " + str(lineNumber/500 +1)
        lineNumber += 1

        if("Name" in line):
            NAME = line[18:]
        if("Gender" in line):
            GENDER = line[18:]
        if("RegionLost" in line):
            REGIONLOST = line[18:]
        if("PostcodeLost" in line):
            POSTCODE = line[18:]
            g = geocoder.google(POSTCODE + ", UK")
            if(g):
                LATITUDE = g.lat
                LONGITUDE = g.lng
        if("Breed" in line):
            BREED = line[18:]
        if("ID              :" in line):
            ID = line[18:]

            fullId = line[18:].replace('\n', '').replace(' ','')
            shortenedId = fullId[:-3]
            if(fullId and shortenedId):
                IMAGEADDRESS = ("http://www.james-odonnell.com/LostPets/DatabaseScripts/images/%s.jpg" % fullId)
            else:
                IMAGEADDRESS = ''

        
        if("DateLatest Alert Date" in line):
            try:
               x.execute("""INSERT INTO DogLostFeedStore VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)""",(NAME, GENDER, REGIONLOST, POSTCODE, BREED, LATITUDE, LONGITUDE,ID,IMAGEADDRESS))
               #print("did something")
               conn.commit()
            except:
                #print("failed")
                conn.rollback()
                
                          
    try:
        x.execute("""TRUNCATE TABLE `temp`; INSERT INTO `temp`(SELECT DISTINCT * FROM `DogLostFeedStore`); TRUNCATE TABLE `DogLostFeedStore`; INSERT INTO `DogLostFeedStore` (SELECT * FROM `temp`); TRUNCATE TABLE `temp`;""")

    except: 
        print("Failed to do weird truncate thing that I think prevents duplicates")
        conn.rollback()

        
    conn.close()
    print "Finished DogLost inserter"
