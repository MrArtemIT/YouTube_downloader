import time
import sys
import pytube
import os
from pytube import Playlist
import re
#from moviepy.editor import VideoFileClip, AudioFileClip, concatenate_videoclips


def main(url, item):


    if url=="test":
        time.sleep(0.5)
    else:

        dire = ("/storage/emulated/0/Download")

        playlist = Playlist(url)
        playlist._video_regex = re.compile(r"\"url\":\"(/watch\?v=[\w-]*)")  
        for video in playlist.videos:
            print('downloading : {} with url : {}'.format(video.title, video.watch_url))
            video.streams.\
            filter(type='video', progressive=True, file_extension='mp4').\
            order_by('resolution').\
            desc().\
            first().\
            download(dire)