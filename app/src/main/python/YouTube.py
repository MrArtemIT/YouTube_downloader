import pytube


def main(url):


    dire = ("/storage/emulated/0/Download")

    yt = pytube.YouTube(url)
    ft = yt.title
    vid = yt.streams.get_highest_resolution()

    va = yt.author
    vd = yt.description

    vid.download(dire)
    return "(Debug massage)Ну, наверное скачалось"