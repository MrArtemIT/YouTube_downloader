import time
import sys
import pytube
import os
from pytubefix import YouTube
from rutube import Rutube
#from moviepy.editor import VideoFileClip, AudioFileClip, concatenate_videoclips
pytube.request.default_range_size = 1048576 

def main(url, strung_fix_mode, string_rutube_mode):
    '''global vid            
    yt = pytube.YouTube(url)            
    vid = yt.streams.get_highest_resolution()  '''

    dire = ("/storage/emulated/0/Download")
    if url == "test":
        time.sleep(0.9)
    elif string_rutube_mode == "true":
        rt = Rutube(url)
        rt.get_best().download(dire)
    elif strung_fix_mode == "true":
        yt = YouTube(url)
        vid = yt.streams.get_highest_resolution()
        vid.download(dire)
    else:



        yt = pytube.YouTube(url)
        #ft = yt.title
        vid = yt.streams.get_highest_resolution()
        vid.download(dire)
        '''if item_static=="Video":
            vid = yt.streams.get_highest_resolution()
            vid.download(dire)

        
        else:
            vid = yt.streams.filter(only_audio=True).desc().first()
            out_file = vid.download(dire)
            # save the file
            base, ext = os.path.splitext(out_file)
            new_file = base + '.mp3'
            os.rename(out_file, new_file)'''
      
'''def progress(stream=None, chunk=None, remaining=0):
    
    file_size = vid.filesize
    file_downloaded = (file_size - remaining)
    per = (file_downloaded / file_size) * 100'''

