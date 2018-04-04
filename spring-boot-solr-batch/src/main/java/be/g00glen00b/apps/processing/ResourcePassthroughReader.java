package be.g00glen00b.apps.processing;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class ResourcePassthroughReader implements ResourceAwareItemReaderItemStream<Resource> {
    private Resource resource;
    private boolean read = false;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        this.read = false;
    }

    @Override
    public Resource read() {
        if (read) {
            return null;
        } else {
            read = true;
            return resource;
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {
    }
}
