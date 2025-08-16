package personal.yoinami.wowza_spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import personal.yoinami.wowza_spring.service.VideoService;


@Controller
public class HomeController {

    private final VideoService videoService;

    @Value("${wowza.api.baseurl}")
    String wowzaBaseurl;
    // exmple url: http://192.168.0.111:1935

    public HomeController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/")
    public String listVideos(Model model) {
        
        System.out.println("Hello Project");
        model.addAttribute("videos", videoService.listVideoFiles());
        return "index";
    }

    @GetMapping("/stream/{videoName}/{videoQuality}")
    public String streamVideo(@PathVariable String videoName, @PathVariable String videoQuality, Model model) {


        if(videoQuality.isBlank()) {
            videoQuality = "";
        } else {
            videoQuality = "_" + videoQuality + "p";
        }

        String streamUrl = wowzaBaseurl + "/live/mp4:test" + videoQuality + "/playlist.m3u8";


        model.addAttribute("streamUrl", streamUrl);
        model.addAttribute("videoName", videoName);

        return "player";
    }
}
