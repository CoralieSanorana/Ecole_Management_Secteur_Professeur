  /* ============================================================
     ROLE CONFIGURATIONS
  ============================================================ */
  const roles = {
    directeur: {
      name: 'M. Rakoto',
      role: 'Directeur',
      initials: 'DR',
      defaultPage: 'dir-dashboard',
      nav: 'nav-directeur'
    },
    secretariat: {
      name: 'Mme. Rasoa',
      role: 'Secrétaire',
      initials: 'RS',
      defaultPage: 'sec-paiements',
      nav: 'nav-secretariat'
    },
    professeur: {
      name: 'Prof. Rabe',
      role: 'Professeur',
      initials: 'RB',
      defaultPage: 'prof-emploi',
      nav: 'nav-professeur'
    },
    etudiant: {
      name: 'Rakoto Jean',
      role: 'Étudiant',
      initials: 'RJ',
      defaultPage: 'etu-emploi',
      nav: 'nav-etudiant'
    }
  };

  const pageTitles = {
    'dir-dashboard': 'Tableau de bord',
    'dir-finances': 'Finances & Bénéfices',
    'dir-professeurs': 'Corps Professoral',
    'dir-prof-profil': 'Profil Professeur',
    'dir-ecolages': 'Écolages du Mois',
    'sec-paiements': 'Ajouter un Paiement',
    'sec-bilan': 'Bilan de Paiement',
    'sec-eleves': 'Liste des Élèves',
    'sec-profils': 'Profil Élève',
    'prof-emploi': 'Emploi du Temps',
    'prof-notes': 'Notes des Élèves',
    'prof-devoirs': 'Devoirs & Leçons',
    'prof-bulletins': 'Bulletins',
    'prof-profil': 'Mon Profil',
    'etu-emploi': 'Mon Emploi du Temps',
    'etu-notes': 'Mes Notes',
    'etu-bulletin': 'Mon Bulletin',
    'etu-devoirs': 'Devoirs & Leçons',
    'actualites': 'Actualités',
    'notifications-page': 'Notifications',
  };

  /* ============================================================
     PAGE PATHS MAPPING
  ============================================================ */
  const pagePaths = {
    // Directeur
    'dir-dashboard': '../Directeur/dashboard.html',
    'dir-finances': '../Directeur/finances.html',
    'dir-professeurs': '../Directeur/professeurs.html',
    'dir-prof-profil': '../Directeur/profil_professeur.html',
    'dir-ecolages': '../Directeur/ecolages.html',
    // Secretariat
    'sec-paiements': '../Secretaire/paiement.html',
    'sec-bilan': '../Secretaire/bilan.html',
    'sec-eleves': '../Secretaire/eleves.html',
    'sec-profils': '../Secretaire/profil_eleve.html',
    // Professeur
    'prof-emploi': '../Professeur/calendar.html',
    'prof-notes': '../Professeur/notes.html',
    'prof-devoirs': '../Professeur/devoirs.html',
    'prof-bulletins': '../Professeur/bulletin.html',
    'prof-profil': '../Professeur/profil.html',
    // Etudiant
    'etu-emploi': '../Etudiant/calendar.html',
    'etu-notes': '../Etudiant/notes.html',
    'etu-bulletin': '../Etudiant/bulletin.html',
    'etu-devoirs': '../Etudiant/devoirs.html',
    // Communs
    'actualites': '../communs/actualites.html',
    'notifications-page': '../communs/notifications.html',
  };

  /* ============================================================
     SWITCH ROLE
  ============================================================ */
  function switchRole(roleKey) {
    const r = roles[roleKey];
    if (!r) return;

    // Update user info
    document.getElementById('sidebar-user-name').textContent = r.name;
    document.getElementById('sidebar-user-role').textContent = r.role;
    document.getElementById('sidebar-avatar-initials').textContent = r.initials;
    document.getElementById('topbar-user-name').textContent = r.name;
    document.getElementById('topbar-user-role').textContent = r.role;
    document.getElementById('topbar-avatar-initials').textContent = r.initials;

    // Show/hide navs
    ['nav-directeur','nav-secretariat','nav-professeur','nav-etudiant'].forEach(id => {
      document.getElementById(id).style.display = 'none';
    });
    document.getElementById(r.nav).style.display = 'block';

    // Show default page
    showPage(r.defaultPage);

    showToast('🔄 Vue changée : ' + r.role);
  }

  /* ============================================================
     SHOW PAGE
  ============================================================ */
  async function showPage(pageId) {
    const mainContent = document.querySelector('.main-content');
    const pagePath = pagePaths[pageId];
    
    if (!pagePath) {
      console.error('Page path not found for:', pageId);
      return;
    }

    // Update nav items
    document.querySelectorAll('.nav-item').forEach(item => {
      item.classList.remove('active');
    });
    document.querySelectorAll('.nav-item').forEach(item => {
      const onclick = item.getAttribute('onclick') || '';
      if (onclick.includes(`'${pageId}'`)) item.classList.add('active');
    });

    // Update title
    const title = pageTitles[pageId] || 'LycéePro';
    document.getElementById('topbar-title').textContent = title;

    try {
      // Fetch page content
      const response = await fetch(pagePath);
      if (!response.ok) throw new Error('Page not found');
      
      const html = await response.text();
      
      // Fade out current content
      mainContent.style.opacity = '0';
      mainContent.style.transform = 'translateY(10px)';
      mainContent.style.transition = 'all 0.2s ease';
      
      setTimeout(() => {
        // Update content
        mainContent.innerHTML = html;
        
        // Fade in new content
        mainContent.style.opacity = '1';
        mainContent.style.transform = 'translateY(0)';
      }, 200);
      
    } catch (error) {
      console.error('Error loading page:', error);
      mainContent.innerHTML = `
        <div style="text-align:center;padding:var(--sp-2xl);">
          <i class="fas fa-exclamation-triangle" style="font-size:48px;color:var(--danger);margin-bottom:var(--sp-lg);"></i>
          <h3>Erreur de chargement</h3>
          <p style="color:var(--txt2);">Impossible de charger la page demandée.</p>
        </div>
      `;
    }
  }

  /* ============================================================
     MODALS
  ============================================================ */
  function openModal(id) {
    document.getElementById(id).classList.add('open');
  }
  function closeModal(id) {
    document.getElementById(id).classList.remove('open');
  }
  // Close on backdrop click
  document.querySelectorAll('.modal-backdrop').forEach(backdrop => {
    backdrop.addEventListener('click', (e) => {
      if (e.target === backdrop) backdrop.classList.remove('open');
    });
  });

  /* ============================================================
     NOTIFICATIONS
  ============================================================ */
  function toggleNotif() {
    document.getElementById('notif-dropdown').classList.toggle('open');
  }
  document.addEventListener('click', (e) => {
    const notifPanel = document.querySelector('.notif-panel');
    const dropdown = document.getElementById('notif-dropdown');
    if (!e.target.closest('.topbar-btn') || !dropdown.contains(e.target)) {
      if (!e.target.closest('.topbar-btn')) {
        dropdown.classList.remove('open');
      }
    }
  });

  /* ============================================================
     TOAST
  ============================================================ */
  function showToast(message, duration = 3000) {
    const container = document.getElementById('toast-container');
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.innerHTML = message;
    container.appendChild(toast);
    setTimeout(() => {
      toast.style.opacity = '0';
      toast.style.transform = 'translateX(20px)';
      toast.style.transition = 'all .3s ease';
      setTimeout(() => toast.remove(), 300);
    }, duration);
  }

  /* ============================================================
     POLL SELECTION
  ============================================================ */
  function selectPoll(el) {
    const parent = el.parentElement;
    parent.querySelectorAll('.poll-option').forEach(o => o.classList.remove('selected'));
    el.classList.add('selected');
  }

  /* ============================================================
     FILTER PILLS (interactive)
  ============================================================ */
  document.querySelectorAll('.filter-pills').forEach(group => {
    group.querySelectorAll('.pill').forEach(pill => {
      pill.addEventListener('click', () => {
        group.querySelectorAll('.pill').forEach(p => p.classList.remove('active'));
        pill.classList.add('active');
      });
    });
  });

  /* ============================================================
     TABS
  ============================================================ */
  document.querySelectorAll('.tabs').forEach(tabs => {
    tabs.querySelectorAll('.tab').forEach(tab => {
      tab.addEventListener('click', () => {
        tabs.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        tab.classList.add('active');
      });
    });
  });

  /* ============================================================
     KEYBOARD: ESC closes modal
  ============================================================ */
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
      document.querySelectorAll('.modal-backdrop.open').forEach(m => m.classList.remove('open'));
      document.getElementById('notif-dropdown').classList.remove('open');
    }
  });

  /* ============================================================
     THEME TOGGLE
  ============================================================ */
  const themeToggle = document.getElementById('themeToggle');
  let isDark = false;
  themeToggle.addEventListener('click', () => {
    isDark = !isDark;
    document.documentElement.setAttribute('data-theme', isDark ? 'dark' : 'light');
    themeToggle.innerHTML = isDark ? '<i class="fas fa-sun"></i>' : '<i class="fas fa-moon"></i>';
    showToast(isDark ? '🌙 Thème sombre activé' : '☀️ Thème clair activé');
  });

  /* ============================================================
     PARTICLE CANVAS
  ============================================================ */
  (function initParticles() {
    const canvas = document.getElementById('particle-canvas');
    const ctx = canvas.getContext('2d');
    let W, H, particles = [], mouse = { x: -999, y: -999 };

    function resize() {
      W = canvas.width  = window.innerWidth;
      H = canvas.height = window.innerHeight;
    }
    resize();
    window.addEventListener('resize', resize);
    window.addEventListener('mousemove', e => { mouse.x = e.clientX; mouse.y = e.clientY; });

    const COLORS = ['#00FF85', '#0A4DFF', '#00D46E', '#4D7AFF', '#00FF8599'];

    function Particle() {
      this.reset();
    }
    Particle.prototype.reset = function() {
      this.x  = Math.random() * W;
      this.y  = Math.random() * H;
      this.r  = Math.random() * 2.2 + .4;
      this.vx = (Math.random() - .5) * .35;
      this.vy = (Math.random() - .5) * .35;
      this.color = COLORS[Math.floor(Math.random() * COLORS.length)];
      this.life  = 1;
    };

    for (let i = 0; i < 80; i++) particles.push(new Particle());

    function draw() {
      ctx.clearRect(0, 0, W, H);

      // Draw connections
      for (let i = 0; i < particles.length; i++) {
        const a = particles[i];
        for (let j = i + 1; j < particles.length; j++) {
          const b = particles[j];
          const dx = a.x - b.x, dy = a.y - b.y;
          const dist = Math.sqrt(dx*dx + dy*dy);
          if (dist < 120) {
            ctx.beginPath();
            ctx.strokeStyle = `rgba(0,255,133,${.12 * (1 - dist/120)})`;
            ctx.lineWidth = .5;
            ctx.moveTo(a.x, a.y);
            ctx.lineTo(b.x, b.y);
            ctx.stroke();
          }
        }

        // Mouse interaction
        const mx = a.x - mouse.x, my = a.y - mouse.y;
        const md = Math.sqrt(mx*mx + my*my);
        if (md < 100) {
          a.vx += mx / md * .04;
          a.vy += my / md * .04;
        }

        a.vx *= .98; a.vy *= .98;
        a.x += a.vx; a.y += a.vy;

        if (a.x < 0 || a.x > W) a.vx *= -1;
        if (a.y < 0 || a.y > H) a.vy *= -1;

        ctx.beginPath();
        ctx.arc(a.x, a.y, a.r, 0, Math.PI*2);
        ctx.fillStyle = a.color;
        ctx.fill();
      }

      requestAnimationFrame(draw);
    }
    draw();
  })();

  /* Init */
  window.addEventListener('DOMContentLoaded', () => {
    showPage('dir-dashboard');
    setTimeout(() => showToast('👋 Bienvenue sur LycéePro !'), 800);
  });