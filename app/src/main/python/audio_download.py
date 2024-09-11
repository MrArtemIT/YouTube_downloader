import time
import sys
import pytube
from pytubefix import YouTube
import os
#from moviepy.editor import VideoFileClip, AudioFileClip, concatenate_videoclips

dire = ("/storage/emulated/0/Download")
def main(url, fix_mode):


    if url=="test":
        time.sleep(0.5)
    elif fix_mode == "true":
        yt = YouTube(url)
        vid = yt.streams.filter(only_audio=True).desc().first()
        out_file = vid.download(dire)
        # save the file
        base, ext = os.path.splitext(out_file)
        new_file = base + '.mp3'
        os.rename(out_file, new_file)
    else:
        yt = pytube.YouTube(url)
        vid = yt.streams.filter(only_audio=True).desc().first()
        out_file = vid.download(dire)
        # save the file
        base, ext = os.path.splitext(out_file)
        new_file = base + '.mp3'
        os.rename(out_file, new_file)
