import time
import sys
import pytube
import os
#from moviepy.editor import VideoFileClip, AudioFileClip, concatenate_videoclips
pytube.request.default_range_size = 1048576 

def main(url, item):
    '''global vid            
    yt = pytube.YouTube(url)            
    vid = yt.streams.get_highest_resolution()  '''    
    

    if url=="test":
        time.sleep(0.5)
    else:

        dire = ("/storage/emulated/0/Download")

        yt = pytube.YouTube(url)
        #ft = yt.title

        if item=="Video":
            vid = yt.streams.get_highest_resolution()
            vid.download(dire)

        
        else:
            vid = yt.streams.filter(only_audio=True).desc().first()
            out_file = vid.download(dire)
            # save the file
            base, ext = os.path.splitext(out_file)
            new_file = base + '.mp3'
            os.rename(out_file, new_file)
      
'''def progress(stream=None, chunk=None, remaining=0):
    
    file_size = vid.filesize
    file_downloaded = (file_size - remaining)
    per = (file_downloaded / file_size) * 100'''

