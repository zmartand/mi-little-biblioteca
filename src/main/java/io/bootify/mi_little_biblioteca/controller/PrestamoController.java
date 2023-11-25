package io.bootify.mi_little_biblioteca.controller;

import io.bootify.mi_little_biblioteca.domain.Bibliotecario;
import io.bootify.mi_little_biblioteca.domain.Lector;
import io.bootify.mi_little_biblioteca.domain.Libro;
import io.bootify.mi_little_biblioteca.model.PrestamoDTO;
import io.bootify.mi_little_biblioteca.repos.BibliotecarioRepository;
import io.bootify.mi_little_biblioteca.repos.LectorRepository;
import io.bootify.mi_little_biblioteca.repos.LibroRepository;
import io.bootify.mi_little_biblioteca.service.PrestamoService;
import io.bootify.mi_little_biblioteca.util.CustomCollectors;
import io.bootify.mi_little_biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;
    private final BibliotecarioRepository bibliotecarioRepository;

    public PrestamoController(final PrestamoService prestamoService,
            final LibroRepository libroRepository, final LectorRepository lectorRepository,
            final BibliotecarioRepository bibliotecarioRepository) {
        this.prestamoService = prestamoService;
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("libroValues", libroRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Libro::getId, Libro::getTitulo)));
        model.addAttribute("lectorValues", lectorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lector::getId, Lector::getNombre)));
        model.addAttribute("bibliotecarioValues", bibliotecarioRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Bibliotecario::getId, Bibliotecario::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("prestamoes", prestamoService.findAll());
        return "prestamo/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("prestamo") final PrestamoDTO prestamoDTO) {
        return "prestamo/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("prestamo") @Valid final PrestamoDTO prestamoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "prestamo/add";
        }
        prestamoService.create(prestamoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("prestamo.create.success"));
        return "redirect:/prestamos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("prestamo", prestamoService.get(id));
        return "prestamo/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("prestamo") @Valid final PrestamoDTO prestamoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "prestamo/edit";
        }
        prestamoService.update(id, prestamoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("prestamo.update.success"));
        return "redirect:/prestamos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        prestamoService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("prestamo.delete.success"));
        return "redirect:/prestamos";
    }

}
