import requests

def poplateImageFolderUsingTextFileIds():
    idList = []
    with open("dogsLostTable.txt", "r") as dogsLostTable:
        for line in dogsLostTable:
            if("ID              :" in line):
                fullId = line[18:].replace('\n', '').replace(' ','')
                shortenedId = fullId[:-3]
                if(fullId and shortenedId):
                    h4x0r_header = {"Referer": "http://dogs.doglost.co.uk"}
                    with open('images/%i.jpg' % int(fullId), 'wb') as f:
                        f.write(requests.get('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (int(shortenedId), int(fullId)), headers=h4x0r_header).content)
                else:
                    print "id not found"
                


def imageInserterGregCode():
    print "Beginning image insertion"
    for i in range(1, 999):
        try:
                folder = int("".join(list(str(i))[:-3]))
        except ValueError:
                folder = 112
        #print ('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, i))
        h4x0r_header = {"Referer": "http://dogs.doglost.co.uk"}
        with open('images/%i.jpg' % (i), 'wb') as f:
                f.write(requests.get('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, int(str(folder) + str(i))), headers=h4x0r_header).content)
                #print ('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, i))
                #f.write(requests.get('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, i), headers=h4x0r_header).content)
        
