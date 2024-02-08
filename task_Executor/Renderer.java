import java.util.List;
import java.util.concurrent.*;

public abstract class Renderer {

    private final ExecutorService executor;

    public Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> info = scanForImageInfo(source);
        ExecutorCompletionService<ImageData> completionService
                = new ExecutorCompletionService<>(executor);
        for (final ImageInfo imageInfo:info) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
            renderText(source);
            try {
                for (int i = 0; i < info.size(); i++) {
                    Future<ImageData> f = completionService.take();
                    ImageData imageData = f.get();
                    renderImage(imageData);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

    }


    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);

}
