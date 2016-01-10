package be.g00glen00b.config;

import be.g00glen00b.dto.BooleanSuperheroAlignmentConverter;
import be.g00glen00b.dto.SuperheroDTO;
import be.g00glen00b.entity.Superhero;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.dozer.loader.api.FieldsMappingOptions.customConverter;

@Configuration
public class MappingConfig {

    @Bean
    public BeanMappingBuilder beanMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Superhero.class, SuperheroDTO.class)
                    .fields("good", "alignment", customConverter(BooleanSuperheroAlignmentConverter.class))
                    .fields(field("id").accessible(), field("id").accessible())
                    .fields("firstName", "identity.firstName")
                    .fields("lastName", "identity.lastName");
            }
        };
    }

    @Bean
    public DozerBeanMapper beanMapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.addMapping(beanMappingBuilder());
        return dozerBeanMapper;
    }
}
