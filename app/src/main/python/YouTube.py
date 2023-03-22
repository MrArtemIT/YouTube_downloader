import pytube
import os


def main(url, item):


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



    #va = yt.author
    #vd = yt.description

    #vid.download(dire)
