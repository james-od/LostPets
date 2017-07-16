import DogLostReader
import DogsLostInserter
import imageInserterGregCode

def main():

    DogLostReader.readFromFirst20PagesOnDogLostWebsite()
    DogsLostInserter.insertDataIntoDogLostFeedStoreTable()
    imageInserterGregCode.poplateImageFolderUsingTextFileIds()
    
if __name__ == "__main__":
    main()
