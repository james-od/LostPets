import requests
for i in range(1, 999):
    try:
            folder = int("".join(list(str(i))[:-3]))
    except ValueError:
            folder = 112
    h4x0r_header = {"Referer": "http://dogs.doglost.co.uk"}
    with open('images/%i.jpg' % (i), 'wb') as f:
            f.write(requests.get('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, int(str(folder) + str(i))), headers=h4x0r_header).content)
            #print ('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, i))
            #f.write(requests.get('http://dogs.doglost.co.uk/%i/%i_a.jpg' % (folder, i), headers=h4x0r_header).content)
    
